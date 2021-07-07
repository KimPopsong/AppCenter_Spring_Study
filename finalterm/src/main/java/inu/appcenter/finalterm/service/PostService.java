package inu.appcenter.finalterm.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import inu.appcenter.finalterm.domain.Category;
import inu.appcenter.finalterm.domain.Image;
import inu.appcenter.finalterm.domain.Member;
import inu.appcenter.finalterm.domain.Post;
import inu.appcenter.finalterm.exception.CategoryException;
import inu.appcenter.finalterm.exception.MemberException;
import inu.appcenter.finalterm.model.post.PostPatchRequest;
import inu.appcenter.finalterm.model.post.PostResponse;
import inu.appcenter.finalterm.model.post.PostSaveRequest;
import inu.appcenter.finalterm.repository.CategoryRepository;
import inu.appcenter.finalterm.repository.ImageRepository;
import inu.appcenter.finalterm.repository.MemberRepository;
import inu.appcenter.finalterm.repository.PostRepository;
import inu.appcenter.finalterm.repository.query.CommentQueryRepository;
import inu.appcenter.finalterm.repository.query.ImageQueryRepository;
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
public class PostService {

    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    private final CommentQueryRepository commentQueryRepository;
    private final ImageQueryRepository imageQueryRepository;
    private final PostQueryRepository postQueryRepository;

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.thumbnailBucket}")
    private String thumbnailBucket;

    @Transactional
    public void savePost(PostSaveRequest postSaveRequest, List<MultipartFile> imageList, Long categoryId, String userEmail) {

        Member member = memberRepository.findByEmail(userEmail)
                .orElseThrow(() -> new MemberException("No Member Exists!"));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryException("No Category Exists!"));

        Post post = Post.createPost(postSaveRequest.getTitle(), postSaveRequest.getContent(), member, category);

        List<Image> imageObjectList = uploadImageS3(imageList, post);

        post.changeImage(imageObjectList);

        postRepository.save(post);
    }

    @Transactional
    public void patchPost(PostPatchRequest postPatchRequest, List<MultipartFile> imageList, Long postId, String userEmail) {

        Post post = postQueryRepository.findPostWithMember(postId);

        if (!post.getMember().getEmail().equals(userEmail)) {
            throw new MemberException("Member does not match!");
        }

        imageQueryRepository.deleteImagesByPostId(postId);  // post에 있는 image 삭제 후

        post.patchPost(postPatchRequest.getTitle(), postPatchRequest.getContent());

        List<Image> imageObjectList = uploadImageS3(imageList, post);  // image 새로 업로드

        post.changeImage(imageObjectList);  // post에 있는 image 변경
    }

    @Transactional
    public void deletePost(Long postId, String userEmail) {

        Post post = postQueryRepository.findPostWithMember(postId);

        if (!post.getMember().getEmail().equals(userEmail)) {
            throw new MemberException("Member does not match!");
        }

        imageQueryRepository.deleteImagesByPostId(post.getId());  // post에 있는 image를 모두 삭제한 뒤
        commentQueryRepository.deleteCommentsByPostId(post.getId());  // post에 달린 comment를 모두 삭제 (다른 사람이 작성한 comment도 post가 삭제되었으니 같이 삭제)
        postRepository.delete(post);  // post 삭제
    }

    public PostResponse findPost(Long postId) {

        Post post = postQueryRepository.findPostWithComments(postId);

        return new PostResponse(post);
    }

    @Transactional
    public void deletePostByADMIN(Long postId) {

        Post post = postQueryRepository.findPostWithImageList(postId);

        imageQueryRepository.deleteImagesByPostId(post.getId());  // post에 있는 image를 모두 삭제한 뒤
        commentQueryRepository.deleteCommentsByPostId(post.getId());  // post에 달린 comment를 모두 삭제 (다른 사람이 작성한 comment도 post가 삭제되었으니 같이 삭제)
        postRepository.delete(post);  // post 삭제
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
