package com.example.userservice.controller;

import com.example.userservice.domain.Member;
import com.example.userservice.model.member.request.MemberSaveRequest;
import com.example.userservice.model.member.request.MemberUpdateRequest;
import com.example.userservice.model.member.response.MemberResponse;
import com.example.userservice.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/members")
    public ResponseEntity saveMember(@RequestBody MemberSaveRequest memberSaveRequest) {  // RequestBody -> Json
        memberService.save(memberSaveRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/members/{memberId}")
    public ResponseEntity updateMember(@PathVariable Long memberId, @RequestBody MemberUpdateRequest memberUpdateRequest) {
        memberService.update(memberId, memberUpdateRequest);

        return ResponseEntity.status(HttpStatus.OK).build();

    }

    @GetMapping("/members")
    public List<MemberResponse> findMembers(){
        List<Member> members = memberService.findAll();

        List<MemberResponse> memberResponses = members.stream()
                .map(member -> new MemberResponse(member))
                .collect(Collectors.toList());

        return memberResponses;
    }

    @GetMapping("/members/{memberId}")
    public MemberResponse findByMemberId(@PathVariable Long memberId){
        Member findMember = memberService.findById(memberId);

        MemberResponse memberResponse = new MemberResponse(findMember);

        return memberResponse;
    }

    @DeleteMapping("/members/{memberId}")
    public ResponseEntity deleteByMemberId(@PathVariable Long memberId){
        memberService.deleteById(memberId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
