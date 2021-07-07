package inu.appcenter.finalterm.controller;

import inu.appcenter.finalterm.config.security.LoginMember;
import inu.appcenter.finalterm.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    /**
     * 게시글 이미지 추가
     *
     * May occur 1 + 1 problem
     *
     * @param imageList
     * @param postId
     * @return
     */
    @PostMapping("/image/{postId}")
    public ResponseEntity<Void> addImage(@RequestPart(required = false) List<MultipartFile> imageList, @PathVariable Long postId, @LoginMember User user) {

        imageService.addImage(imageList, postId, user.getUsername());

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 게시글 이미지 삭제
     *
     * May occur 1 + 1 problem
     *
     * @param postId
     * @param user
     * @return
     */
    @DeleteMapping("/image/{postId}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long postId, @LoginMember User user) {

        imageService.deleteImage(postId, user.getUsername());

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
