package com.example.fifth.repository.query;

import com.example.fifth.domain.Member;
import com.example.fifth.domain.QMember;
import com.example.fifth.domain.TodoStatus;
import com.example.fifth.dto.MemberWithTodoCount;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.fifth.domain.QMember.*;
import static com.example.fifth.domain.QTodo.*;

@Repository  // 스프링 빈으로 등록, DB 오류나 JPA 오류를 스프링 오류로 전환 -> 에러 설명이 깔끔
@RequiredArgsConstructor
public class MemberQueryRepository {

    private final JPAQueryFactory queryFactory;  // 우리가 등록한 JPAQueryFactory

    public Member findMemberById(Long memberId) {
        Member result = queryFactory
                .select(member).distinct()
                .from(member)
                .leftJoin(member.todoList, todo).fetchJoin()
                .where(member.id.eq(memberId)
                        .and(todo.status.eq(TodoStatus.NOTFINISH)))
                .fetchOne();  // 1 개의 결과 값이 확실할 때

        return result;
    }

    public MemberWithTodoCount findMemberWithTodoCountsById(Long memberId) {
        Tuple tuple = queryFactory
                .select(member, todo.count())
                .from(member)
                .leftJoin(member.todoList, todo)
                .groupBy(member)
                .where(member.id.eq(memberId))
                .fetchOne();

        MemberWithTodoCount memberWithTodoCount = new MemberWithTodoCount();
        memberWithTodoCount.setMember(tuple.get(member));
        memberWithTodoCount.setCount(tuple.get(todo.count()));

        return memberWithTodoCount;
    }

    public List<Member> findMembersWithTodo() {
        List<Member> result = queryFactory
                .select(member).distinct()
                .from(member)
                .leftJoin(member.todoList, todo).fetchJoin()
                .fetch();

        return result;
    }
}
