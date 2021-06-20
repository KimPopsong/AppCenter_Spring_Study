package com.example.fifth.service;

import com.example.fifth.domain.Member;
import com.example.fifth.model.member.MemberLoginRequest;
import com.example.fifth.model.member.MemberSaveRequest;
import com.example.fifth.repository.MemberRepository;
import com.example.fifth.repository.query.MemberQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService{

    private final MemberRepository memberRepository;
    private final MemberQueryRepository memberQueryRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void saveMember(MemberSaveRequest memberSaveRequest) {

        // 이메일 검증 생략
        Member member = Member.createMember(memberSaveRequest.getEmail(), passwordEncoder.encode(memberSaveRequest.getPassword()), memberSaveRequest.getName());

        memberRepository.save(member);
    }

    public Member loginMember(MemberLoginRequest memberLoginRequest) {
        Member findMember = memberRepository.findByEmail(memberLoginRequest.getEmail())
                .orElseThrow(() -> new IllegalStateException("No Member Exists"));

        if (!passwordEncoder.matches(memberLoginRequest.getPassword(), findMember.getPassword())) {
            throw new IllegalStateException("Not Match");
        }

        return findMember;
    }

    public List<Member> findMembers() {

        return memberQueryRepository.findMembersWithTodo();
    }
}
