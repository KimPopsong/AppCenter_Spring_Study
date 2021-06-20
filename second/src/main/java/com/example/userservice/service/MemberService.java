package com.example.userservice.service;

import com.example.userservice.domain.Member;
import com.example.userservice.model.member.request.MemberSaveRequest;
import com.example.userservice.model.member.request.MemberUpdateRequest;
import com.example.userservice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void save(MemberSaveRequest memberSaveRequest) {
        Member member = Member.createMember(memberSaveRequest.getName(), memberSaveRequest.getAge());

        memberRepository.save(member);
    }

    @Transactional
    public void update(Long memberId, MemberUpdateRequest memberUpdateRequest) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(IllegalStateException::new);

        findMember.changeMember(memberUpdateRequest.getName(), memberUpdateRequest.getAge());
    }

    public List<Member> findAll() {  // 조회는 Transactional 없음

        return memberRepository.findAll();
    }

    public Member findById(Long memberId) {

        return memberRepository.findById(memberId)
                .orElseThrow(IllegalStateException::new);
    }

    @Transactional
    public void deleteById(Long memberId) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(IllegalStateException::new);

        memberRepository.delete(findMember);
    }
}
