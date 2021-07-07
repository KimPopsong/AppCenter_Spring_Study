package inu.appcenter.finalterm.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import inu.appcenter.finalterm.domain.Comment;
import inu.appcenter.finalterm.domain.QComment;
import inu.appcenter.finalterm.domain.QMember;
import inu.appcenter.finalterm.domain.QPost;
import inu.appcenter.finalterm.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentQueryRepository {

    private final CommentRepository commentRepository;
    private final JPAQueryFactory queryFactory;

    public void deleteCommentsByMemberId(Long memberId) {

        List<Comment> comments = queryFactory
                .select(QComment.comment).distinct()
                .from(QComment.comment)
                .leftJoin(QComment.comment.member, QMember.member).fetchJoin()
                .where(QComment.comment.member.id.eq(memberId))
                .fetch();

        for (Comment comment : comments) {
            commentRepository.delete(comment);
        }
    }

    public void deleteCommentsByPostId(Long postId) {

        List<Comment> comments = queryFactory
                .select(QComment.comment).distinct()
                .from(QComment.comment)
                .leftJoin(QComment.comment.post, QPost.post).fetchJoin()
                .where(QComment.comment.post.id.eq(postId))
                .fetch();

        for (Comment comment : comments) {
            commentRepository.delete(comment);
        }
    }

    public Comment findCommentWithMember(Long commentId) {

        return queryFactory
                .select(QComment.comment).distinct()
                .from(QComment.comment)
                .leftJoin(QComment.comment.member, QMember.member).fetchJoin()
                .where(QComment.comment.id.eq(commentId))
                .fetchOne();
    }
}
