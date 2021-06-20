package com.example.fourth.repository;

import com.example.fourth.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    @Query("select distinct m from Member m join fetch m.orderList where m.id=:memberId")
    Optional<Member> findWithOrderListById(@Param("memberId") Long memberId);

    //TODO
    //오류 나는 이유 적기
    @Query("select distinct m from Member m join fetch m.orderList")
    Optional<Member> findWithOrderListAll();
}
