package com.example.third.controller;

import com.example.third.domain.Member;
import com.example.third.model.member.MemberResponse;
import com.example.third.model.member.MemberSaveRequest;
import com.example.third.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/teams/{teamId}/members")
    public ResponseEntity saveMember(@PathVariable Long teamId, @RequestBody MemberSaveRequest memberSaveRequest) {
        memberService.save(teamId, memberSaveRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //TODO 수정

    //TODO 삭제

    //TODO 조회
    @GetMapping("/members/{memberId}")
    public MemberResponse findById(@PathVariable Long memberId) {
        Member member = memberService.findById(memberId);

        return new MemberResponse(member);
    }

    @GetMapping("/members")
    public List<MemberResponse> findAll(){
        List<Member> members = memberService.findAll();

        return members.stream().map(MemberResponse::new).collect(Collectors.toList());
    }
}
