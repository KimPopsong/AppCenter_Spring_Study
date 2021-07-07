package inu.appcenter.finalterm.controller;

import inu.appcenter.finalterm.config.security.LoginMember;
import inu.appcenter.finalterm.model.post.PostPatchRequest;
import inu.appcenter.finalterm.model.post.PostResponse;
import inu.appcenter.finalterm.model.post.PostSaveRequest;
import inu.appcenter.finalterm.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    /**
     * 게시글 생성 OK
     * <p>
     * really hard... 어렵다... ㅠㅠ
     *
     * @param postSaveRequest title, content must be filled
     * @param imageList       imageList
     * @param categoryId      posts belongs category
     * @param user            post writer
     * @return OK
     */
    @PostMapping("/{categoryId}/post")
    public ResponseEntity<Void> savePost(@RequestPart(required = false) PostSaveRequest postSaveRequest,
                                         @RequestPart(required = false) List<MultipartFile> imageList,
                                         @PathVariable Long categoryId,
                                         @LoginMember User user) {

        postService.savePost(postSaveRequest, imageList, categoryId, user.getUsername());

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 게시글 수정 OK
     *
     * @param postPatchRequest title, content must be filled
     * @param imageList        imageList
     * @param postId           want to modify
     * @param user             writer
     * @return OK
     */
    @PatchMapping("/post/{postId}")
    public ResponseEntity<Void> patchPost(@RequestPart(required = false) PostPatchRequest postPatchRequest,
                                          @RequestPart(required = false) List<MultipartFile> imageList,
                                          @PathVariable Long postId,
                                          @LoginMember User user) {

        postService.patchPost(postPatchRequest, imageList, postId, user.getUsername());

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 게시글 삭제 OK
     *
     * @param postId want to delete
     * @param user   writer
     * @return OK
     */
    @DeleteMapping("/post/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId, @LoginMember User user) {

        postService.deletePost(postId, user.getUsername());

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 게시글 상세 조회 NOT OK...
     *
     * 어 렵 다 !
     *
     * LazyInitializationException 발생. 영속성 컨텍스트 어쭈구 문제라고 해서 PostService에서 PostResponse를 받아오는 것으로 해결.
     * MultipleBagFetchException 발생. ToMany에 fetchJoin을 여러 번 사용하면 발생한다고 함. imageList를 따로 호출하기 때문에 N + 1 문제 발생 ㅠㅠ...
     *
     * @param postId want to find
     * @return PostResponse -> Post with writer's name, comments and images.
     */
    @GetMapping("/post/{postId}")
    public PostResponse findPost(@PathVariable Long postId) {

        return postService.findPost(postId);
    }

    /**
     * 게시글 삭제 OK... N + 1...?
     * ADMIN ONLY
     *
     * @param postId
     * @return
     */
    @DeleteMapping("/ADMIN/post/{postId}")
    public ResponseEntity<Void> deletePostByADMIN(@PathVariable Long postId) {

        postService.deletePostByADMIN(postId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
