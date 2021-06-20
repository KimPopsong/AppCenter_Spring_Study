package inu.appcenter.seventh.service.impl;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import inu.appcenter.seventh.domain.Image;
import inu.appcenter.seventh.domain.Member;
import inu.appcenter.seventh.model.MemberCreateRequest;
import inu.appcenter.seventh.model.MemberResponse;
import inu.appcenter.seventh.repository.MemberRepository;
import inu.appcenter.seventh.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.thumbnailBucket}")
    private String thumbnailBucket;

    @Override
    public MemberResponse create(MultipartFile image, MemberCreateRequest request) {

        Image imageObject = uploadImageS3(image);

        Member member = Member.createMember(request.getName());
        member.changeImage(imageObject);

        memberRepository.save(member);

        return MemberResponse.from(member);
    }

    private Image uploadImageS3(MultipartFile image) {

        Image imageObject = null;

        if (!image.isEmpty()) {
            if (!image.getContentType().startsWith("image")) {
                throw new IllegalStateException();
            }

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(image.getSize());
            objectMetadata.setContentType(image.getContentType());

            String imageStoreName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();

            try {
                amazonS3Client.putObject(new PutObjectRequest(bucket, imageStoreName, image.getInputStream(), objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead));

                String profileImageUrl = amazonS3Client.getUrl(bucket, imageStoreName).toString();

                String thumbnailImageUrl = amazonS3Client.getUrl(thumbnailBucket, imageStoreName).toString();

                imageObject = Image.create(profileImageUrl, thumbnailImageUrl, imageStoreName);

            } catch (IOException exception) {
                log.error(exception.getMessage());
            }
        }

        return imageObject;
    }
}
