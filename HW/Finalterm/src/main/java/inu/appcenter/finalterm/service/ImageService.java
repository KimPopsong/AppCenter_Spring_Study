package inu.appcenter.finalterm.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import inu.appcenter.finalterm.domain.Image;
import inu.appcenter.finalterm.domain.Post;
import inu.appcenter.finalterm.exception.MemberException;
import inu.appcenter.finalterm.repository.ImageRepository;
import inu.appcenter.finalterm.repository.query.PostQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final PostQueryRepository postQueryRepository;

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.thumbnailBucket}")
    private String thumbnailBucket;

    @Transactional
    public void addImage(List<MultipartFile> imageList, Long postId, String userEmail) {

        Post post = postQueryRepository.findPostWithImageList(postId);

        if (!post.getMember().getEmail().equals(userEmail)) {
            throw new MemberException("Member does not match!");
        }

        List<Image> originImageList = post.getImageList();

        List<Image> addImageList = uploadImageS3(imageList, post);

        originImageList.addAll(addImageList);

        post.changeImage(originImageList);
    }

    @Transactional
    public void deleteImage(Long postId, String userEmail) {

        Post post = postQueryRepository.findPostWithImageList(postId);

        if (!post.getMember().getEmail().equals(userEmail)) {
            throw new MemberException("Member does not match!");
        }

        List<Image> imageList = post.getImageList();

        Image image = imageList.get(imageList.size() - 1);

        imageList.remove(imageList.size() - 1);  // Delete last image from Post

        imageRepository.delete(image);  // Delete image from ImageRepository

        post.changeImage(imageList);
    }

    private List<Image> uploadImageS3(List<MultipartFile> imageList, Post post) {

        List<Image> imageObjectList = new ArrayList<>();

        for (int i = 0; i < imageList.size(); i++) {

            Image imageObject = null;
            MultipartFile image = imageList.get(i);

            if (!image.isEmpty()) {
                if (!image.getContentType().startsWith("image")) {
                    throw new IllegalStateException();
                }

                ObjectMetadata objectMetadata = new ObjectMetadata();
                objectMetadata.setContentLength(image.getSize());
                objectMetadata.setContentType(image.getContentType());

                String storeName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();

                try {
                    amazonS3Client.putObject(new PutObjectRequest(bucket, storeName, image.getInputStream(), objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead));

                    String imageUrl = amazonS3Client.getUrl(bucket, storeName).toString();

                    String thumbnailImageUrl = amazonS3Client.getUrl(thumbnailBucket, storeName).toString();

                    imageObject = Image.create(storeName, imageUrl, thumbnailImageUrl, post);

                    imageRepository.save(imageObject);

                } catch (IOException exception) {
                    log.error(exception.getMessage());
                }
            }

            imageObjectList.add(i, imageObject);
        }

        return imageObjectList;
    }
}
