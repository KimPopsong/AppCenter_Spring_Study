package appcenter.sixth.service;

import appcenter.sixth.domain.Member;
import appcenter.sixth.model.MemberCreateRequest;
import appcenter.sixth.model.MemberUpdateRequest;
import appcenter.sixth.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member saveMember(MemberCreateRequest memberCreateRequest) {
        Member member = Member.createMember(memberCreateRequest.getName(), memberCreateRequest.getAge());

        Member savedMember = memberRepository.save(member);

        return savedMember;
    }

    @Transactional
    public Member updateMember(long memberId, MemberUpdateRequest memberUpdateRequest) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(IllegalStateException::new);

        findMember.update(memberUpdateRequest.getName(), memberUpdateRequest.getAge());

        return findMember;
    }

    public boolean deleteMember(long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(IllegalStateException::new);

        member.delete();

        return true;
    }
}
