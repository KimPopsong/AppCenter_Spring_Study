package inu.appcenter.finalterm.controller;

import inu.appcenter.finalterm.config.security.JwtTokenProvider;
import inu.appcenter.finalterm.config.security.LoginMember;
import inu.appcenter.finalterm.domain.Member;
import inu.appcenter.finalterm.domain.Post;
import inu.appcenter.finalterm.model.member.MemberLoginRequest;
import inu.appcenter.finalterm.model.member.MemberPatchRequest;
import inu.appcenter.finalterm.model.member.MemberResponse;
import inu.appcenter.finalterm.model.member.MemberSaveRequest;
import inu.appcenter.finalterm.model.post.PostDTO;
import inu.appcenter.finalterm.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 회원 가입 OK
     *
     * @param memberSaveRequest email, password, nickName. email must be email form and password, nickName must not blank.
     * @return ResponseEntity
     */
    @PostMapping("/members")
    public ResponseEntity<Void> saveMember(@RequestBody @Valid MemberSaveRequest memberSaveRequest) {
        memberService.saveMember(memberSaveRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 로그인 OK
     *
     * @param memberLoginRequest email, password. These two must not blank.
     * @return jwtToken
     */
    @PostMapping("/members/login")
    public ResponseEntity<String> loginMember(@RequestBody @Valid MemberLoginRequest memberLoginRequest) {

        Member findMember = memberService.loginMember(memberLoginRequest);

        String jwtToken = jwtTokenProvider.createToken(String.valueOf(findMember.getId()));

        return ResponseEntity.ok(jwtToken);
    }

    /**
     * 회원 수정 OK
     * <p>
     * Can change password and nickName.
     *
     * @param memberPatchRequest password, nickName. These two must not blank.
     * @param user               Logged-in User's email
     * @return ResponseEntity
     */
    @PatchMapping("/members")
    public ResponseEntity<Void> patchMember(@RequestBody @Valid MemberPatchRequest memberPatchRequest, @LoginMember User user) {

        memberService.patchMember(memberPatchRequest, user.getUsername());

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 회원 탈퇴 OK But TODO 탈퇴시 즉시 토큰 무효화 구현 필요
     * <p>
     * Change Member's Status and Delete Member's Post, Comment and Image.
     *
     * @param user Logged-in User's email
     * @return ResponseEntity
     */
    @DeleteMapping("/members")
    public ResponseEntity<Void> deleteMember(@LoginMember User user) {

        memberService.deleteMember(user.getUsername());

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 회원 상세 조회 OK
     * <p>
     * Display Member's info.
     * 회원의 게시글 조회가 있으므로 Post나 Comment는 보여주지 않는거로 하였습니다.
     *
     * @param user Logged-in User's email
     * @return MemberResponse - email, nickName, createdAt, updatedAt
     */
    @GetMapping("/members")
    public MemberResponse findMember(@LoginMember User user) {

        Member member = memberService.findMember(user.getUsername());

        return new MemberResponse(member);
    }

    /**
     * 회원의 게시글 조회 NOT OK...
     * <p>
     * 1 + 1 문제 발생...?
     * imageList를 받으려는데 자꾸 에러가 발생해서 빼버렸습니다.
     * comment는 게시글만 조회하는 것이므로 아얘 받아오지 않음
     *
     * @param memberId Long
     * @return Post list
     */
    @GetMapping("/members/{memberId}")
    public List<PostDTO> findMember(@PathVariable Long memberId) {

        List<Post> postList = memberService.findPostsByMemberId(memberId);

        return postList.stream().map(PostDTO::new).collect(Collectors.toList());
    }
}
