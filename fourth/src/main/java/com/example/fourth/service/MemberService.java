package com.example.fourth.service;

import com.example.fourth.domain.Member;
import com.example.fourth.exception.MemberException;
import com.example.fourth.model.member.MemberSaveRequest;
import com.example.fourth.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long saveMember(MemberSaveRequest memberSaveRequest) {
        if(memberRepository.findByEmail(memberSaveRequest.getEmail()).isPresent()){
            throw new MemberException("Already Exists");
        }

        Member member = Member.createMember(memberSaveRequest.getEmail(), memberSaveRequest.getPassword(), memberSaveRequest.getName());

        Member savedMember = memberRepository.save(member);

        return savedMember.getId();
    }
}
