package inu.appcenter.seventh.controller;

import inu.appcenter.seventh.model.MemberCreateRequest;
import inu.appcenter.seventh.model.MemberResponse;
import inu.appcenter.seventh.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/members")
    public ResponseEntity<MemberResponse> create(@RequestPart(required = false) MemberCreateRequest request, @RequestPart(required = false) MultipartFile image) {
        memberService.create(image, request);

        return ResponseEntity.ok(memberService.create(image, request));
    }
}
