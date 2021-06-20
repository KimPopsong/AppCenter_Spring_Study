package com.example.fifth.controller;

import com.example.fifth.config.security.JwtTokenProvider;
import com.example.fifth.domain.Member;
import com.example.fifth.model.member.MemberLoginRequest;
import com.example.fifth.model.member.MemberResponse;
import com.example.fifth.model.member.MemberSaveRequest;
import com.example.fifth.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/members")
    public ResponseEntity<Void> saveMember(@RequestBody MemberSaveRequest memberSaveRequest) {
        memberService.saveMember(memberSaveRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/members/login")
    public ResponseEntity<String> loginMember(@RequestBody MemberLoginRequest memberLoginRequest) {
        Member findMember = memberService.loginMember(memberLoginRequest);

        String jwtToken = jwtTokenProvider.createToken(String.valueOf(findMember.getId()));

        return ResponseEntity.ok(jwtToken);
    }

    @GetMapping("/members")
    public ResponseEntity findMembers() {
        List<Member> members = memberService.findMembers();

        List<MemberResponse> result = members.stream().map(MemberResponse::from).collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }
}
