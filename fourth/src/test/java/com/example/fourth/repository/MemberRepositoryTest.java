package com.example.fourth.repository;

import com.example.fourth.domain.Member;
import com.example.fourth.domain.MemberStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Commit
    @Test
    void MemberTest(){
        Member member = Member.createMember("abcd", "abcd", "DSKIM");

        Member savedMember = memberRepository.save(member);

        assertThat(savedMember.getEmail()).isEqualTo(member.getEmail());
    }

    @Test
    void memberSearch(){
        Member member = memberRepository.findById(1L).get();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.DELETED);
    }
}