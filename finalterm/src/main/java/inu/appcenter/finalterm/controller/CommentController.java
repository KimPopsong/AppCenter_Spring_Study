package inu.appcenter.finalterm.controller;

import inu.appcenter.finalterm.config.security.LoginMember;
import inu.appcenter.finalterm.model.comment.CommentPatchRequest;
import inu.appcenter.finalterm.model.comment.CommentSaveRequest;
import inu.appcenter.finalterm.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /** OK
     * 댓글 생성
     *
     * @param commentSaveRequest content
     * @param postId             where comments wrote
     * @param user               writer
     * @return CREATED
     */
    @PostMapping("/{postId}/comment")
    public ResponseEntity<Void> saveComment(@RequestBody @Valid CommentSaveRequest commentSaveRequest, @PathVariable Long postId, @LoginMember User user) {

        commentService.saveComment(commentSaveRequest, postId, user.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 댓글 수정 OK
     *
     * @param commentPatchRequest content
     * @param commentId           want to patch
     * @param user                writer
     * @return OK
     */
    @PatchMapping("/comment/{commentId}")
    public ResponseEntity<Void> patchComment(@RequestBody @Valid CommentPatchRequest commentPatchRequest, @PathVariable Long commentId, @LoginMember User user) {

        commentService.patchComment(commentId, commentPatchRequest, user.getUsername());

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 댓글 삭제 OK
     *
     * @param commentId want to delete
     * @param user      writer
     * @return OK
     */
    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId, @LoginMember User user) {

        commentService.deleteComment(commentId, user.getUsername());

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 댓글 삭제
     * ADMIN ONLY
     *
     * @param commentId
     * @return
     */
    @DeleteMapping("/ADMIN/comment/{commentId}")
    public ResponseEntity<Void> deleteCommentByADMIN(@PathVariable Long commentId) {

        commentService.deleteCommentByADMIN(commentId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
