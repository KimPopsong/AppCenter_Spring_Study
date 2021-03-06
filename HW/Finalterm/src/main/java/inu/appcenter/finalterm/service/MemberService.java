package inu.appcenter.finalterm.service;

import inu.appcenter.finalterm.domain.Member;
import inu.appcenter.finalterm.domain.Post;
import inu.appcenter.finalterm.domain.status.MemberStatus;
import inu.appcenter.finalterm.exception.MemberException;
import inu.appcenter.finalterm.model.member.MemberLoginRequest;
import inu.appcenter.finalterm.model.member.MemberPatchRequest;
import inu.appcenter.finalterm.model.member.MemberSaveRequest;
import inu.appcenter.finalterm.repository.MemberRepository;
import inu.appcenter.finalterm.repository.PostRepository;
import inu.appcenter.finalterm.repository.query.CommentQueryRepository;
import inu.appcenter.finalterm.repository.query.ImageQueryRepository;
import inu.appcenter.finalterm.repository.query.MemberQueryRepository;
import inu.appcenter.finalterm.repository.query.PostQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    private final MemberQueryRepository memberQueryRepository;
    private final CommentQueryRepository commentQueryRepository;
    private final ImageQueryRepository imageQueryRepository;
    private final PostQueryRepository postQueryRepository;

    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void saveADMIN() {

        if (memberRepository.findByEmail("ADMIN").isEmpty()) {
            Member ADMIN = Member.createADMIN(passwordEncoder.encode("ADMIN"));
            memberRepository.save(ADMIN);
        }
    }

    @Transactional
    public void saveMember(MemberSaveRequest memberSaveRequest) {

        checkDuplicatedEmail(memberSaveRequest.getEmail());

        Member member = Member.createMember(memberSaveRequest.getEmail(), passwordEncoder.encode(memberSaveRequest.getPassword()), memberSaveRequest.getNickName());

        memberRepository.save(member);
    }

    public Member loginMember(MemberLoginRequest memberLoginRequest) {

        Member findMember = findMemberWithEmail(memberLoginRequest.getEmail());

        if (!passwordEncoder.matches(memberLoginRequest.getPassword(), findMember.getPassword())) {
            throw new MemberException("Email or Password does Not Match");
        }

        if (findMember.getStatus().equals(MemberStatus.DELETED)) {  // ????????? ??????????????? ????????? ?????????
            throw new MemberException("Deleted Member");
        }

        return findMember;
    }

    @Transactional
    public void patchMember(MemberPatchRequest memberPatchRequest, String userEmail) {

        Member member = findMemberWithEmail(userEmail);

        member.patch(passwordEncoder.encode(memberPatchRequest.getPassword()), memberPatchRequest.getNickName());
    }

    @Transactional
    public void deleteMember(String userEmail) {

        Member member = memberQueryRepository.findMemberByEmail(userEmail);

        List<Post> postList = member.getPostList();

        for (Post post : postList) {
            imageQueryRepository.deleteImagesByPostId(post.getId());  // post??? ?????? image??? ?????? ????????? ???
            commentQueryRepository.deleteCommentsByPostId(post.getId());
            //postRepository.deleteComments(post.getId());  // post??? ?????? comment??? ?????? ?????? (?????? ????????? ????????? comment??? post??? ?????????????????? ?????? ??????)
            postRepository.delete(post);  // post ??????
        }

        commentQueryRepository.deleteCommentsByMemberId(member.getId());  // member??? comment ??????

        member.delete();  // MemberStatus??? ????????????, ?????? ??? ????????? status??? ??????????????? ????????? ????????? ?????? status??? ????????????????????????.
    }

    public Member findMember(String userEmail) {

        return memberRepository.findByEmail(userEmail)
                .orElseThrow(() -> new MemberException("No Member Exists!"));
    }

    @Transactional
    public List<Post> findPostsByMemberId(Long memberId) {

        return postQueryRepository.findPostsByMemberId(memberId);
    }

    private Member findMemberWithEmail(String email) {  // ???????????? Member?????? ??????

        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException("No Member Exists"));
    }

    private void checkDuplicatedEmail(String email) {  // ????????? ??????????????? ??????

        if (memberRepository.findByEmail(email).isPresent()) {
            throw new MemberException("Email Already Exists!");
        }
    }
}
