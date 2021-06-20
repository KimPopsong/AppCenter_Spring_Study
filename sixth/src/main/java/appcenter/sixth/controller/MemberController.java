package appcenter.sixth.controller;

import appcenter.sixth.domain.Member;
import appcenter.sixth.model.MemberCreateRequest;
import appcenter.sixth.model.MemberResponse;
import appcenter.sixth.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/members")
    public ResponseEntity<MemberResponse> saveMember(@RequestBody MemberCreateRequest memberCreateRequest) {

        Member saveMember = memberService.saveMember(memberCreateRequest);

        return ResponseEntity.ok(MemberResponse.from(saveMember));
    }
}
