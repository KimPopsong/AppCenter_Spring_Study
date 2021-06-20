package com.example.third.service;

import com.example.third.domain.Member;
import com.example.third.domain.Team;
import com.example.third.exception.MemberException;
import com.example.third.exception.TeamException;
import com.example.third.model.member.MemberSaveRequest;
import com.example.third.repository.MemberRepository;
import com.example.third.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;

    @Transactional
    public void save(Long teamId, MemberSaveRequest memberSaveRequest) {
        Team findTeam = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamException("No Team Exists"));

        validatedDuplicatedMemberEmail(memberSaveRequest.getEmail());

        Member member = Member.createMember(memberSaveRequest.getEmail(), memberSaveRequest.getName(), memberSaveRequest.getAge(), findTeam);

        memberRepository.save(member);
    }

    private void validatedDuplicatedMemberEmail(String email) {
        Optional<Member> result = memberRepository.findByEmail(email);

        if (result.isPresent()) {
            throw new MemberException("request memberName is duplicated");
        }
    }

    public Member findById(Long memberId) {
        Member member = memberRepository.findWithTeamById(memberId).orElseThrow(()->new MemberException("No Member Exists"));

        return member;
    }

    public List<Member> findAll() {
        return memberRepository.findWithTeamAll();
    }
}
