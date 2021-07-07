package inu.appcenter.finalterm.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import inu.appcenter.finalterm.domain.*;
import inu.appcenter.finalterm.domain.status.MemberStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Member findMemberByEmail(String memberEmail) {

        return queryFactory
                .select(QMember.member).distinct()
                .from(QMember.member)
                .leftJoin(QMember.member.postList, QPost.post).fetchJoin()
                .leftJoin(QPost.post.commentList, QComment.comment).fetchJoin()
                .where(QMember.member.email.eq(memberEmail)
                        .and(QMember.member.status.eq(MemberStatus.ACTIVATED)))
                .fetchOne();
    }

    public Member findMemberWithPosts(Long memberId) {

        return queryFactory
                .select(QMember.member).distinct()
                .from(QMember.member)
                .leftJoin(QMember.member.postList, QPost.post).fetchJoin()
                .leftJoin(QPost.post.category, QCategory.category).fetchJoin()
                //.leftJoin(QPost.post.imageList, QImage.image).fetchJoin()
                .where(QMember.member.id.eq(memberId))
                .fetchOne();
    }
}
