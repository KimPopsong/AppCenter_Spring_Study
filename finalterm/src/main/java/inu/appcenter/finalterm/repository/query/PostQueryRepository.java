package inu.appcenter.finalterm.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import inu.appcenter.finalterm.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<Post> findPostsByMemberId(Long memberId) {

        return queryFactory
                .select(QPost.post).distinct()
                .from(QPost.post)
                .leftJoin(QPost.post.category, QCategory.category).fetchJoin()
                .leftJoin(QPost.post.imageList, QImage.image).fetchJoin()
                .where(QPost.post.member.id.eq(memberId))
                .fetch();
    }

    public Post findPostWithMember(Long postId) {

        return queryFactory
                .select(QPost.post).distinct()
                .from(QPost.post)
                .leftJoin(QPost.post.member, QMember.member).fetchJoin()
                .where(QPost.post.id.eq(postId))
                .fetchOne();
    }

    public Post findPostWithComments(Long postId) {

        return queryFactory
                .select(QPost.post).distinct()
                .from(QPost.post)
                .leftJoin(QPost.post.member, QMember.member).fetchJoin()
                .leftJoin(QPost.post.commentList, QComment.comment).fetchJoin()
                //.leftJoin(QPost.post.imageList, QImage.image).fetchJoin()
                .where(QPost.post.id.eq(postId))
                .fetchOne();
    }

    public Post findPostWithImageList(Long postId) {

        return queryFactory
                .select(QPost.post).distinct()
                .from(QPost.post)
                .leftJoin(QPost.post.imageList, QImage.image).fetchJoin()
                .where(QPost.post.id.eq(postId))
                .fetchOne();
    }

    public List<Post> findPostsByCategoryId(Long categoryId) {

        return queryFactory
                .select(QPost.post).distinct()
                .from(QPost.post)
                .leftJoin(QPost.post.imageList, QImage.image).fetchJoin()
                //.leftJoin(QPost.post.commentList, QComment.comment).fetchJoin()
                .where(QPost.post.category.id.eq(categoryId))
                .fetch();
    }
}
