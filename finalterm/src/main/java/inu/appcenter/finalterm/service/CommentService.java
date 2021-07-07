package inu.appcenter.finalterm.service;

import inu.appcenter.finalterm.domain.Comment;
import inu.appcenter.finalterm.domain.Member;
import inu.appcenter.finalterm.domain.Post;
import inu.appcenter.finalterm.exception.MemberException;
import inu.appcenter.finalterm.exception.PostException;
import inu.appcenter.finalterm.model.comment.CommentPatchRequest;
import inu.appcenter.finalterm.model.comment.CommentSaveRequest;
import inu.appcenter.finalterm.repository.CommentRepository;
import inu.appcenter.finalterm.repository.MemberRepository;
import inu.appcenter.finalterm.repository.PostRepository;
import inu.appcenter.finalterm.repository.query.CommentQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CommentQueryRepository commentQueryRepository;

    @Transactional
    public void saveComment(CommentSaveRequest commentSaveRequest, Long postId, String userEmail) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException("No Post Exists!"));
        System.out.println(userEmail);
        Member member = memberRepository.findByEmail(userEmail)
                .orElseThrow(() -> new MemberException("No Member Exists!"));

        Comment comment = Comment.createComment(commentSaveRequest.getContent(), member, post);

        commentRepository.save(comment);
    }

    @Transactional
    public void patchComment(Long commentId, CommentPatchRequest commentPatchRequest, String userEmail) {

        Comment comment = commentQueryRepository.findCommentWithMember(commentId);

        if (!comment.getMember().getEmail().equals(userEmail)) {
            throw new MemberException("Member does not match!");
        }

        comment.patch(commentPatchRequest.getContent());
    }

    @Transactional
    public void deleteComment(Long commentId, String userEmail) {

        Comment comment = commentQueryRepository.findCommentWithMember(commentId);

        if (!comment.getMember().getEmail().equals(userEmail)) {
            throw new MemberException("Member does not match!");
        }

        commentRepository.delete(comment);
    }

    @Transactional
    public void deleteCommentByADMIN(Long commentId) {

        commentRepository.deleteById(commentId);
    }
}
