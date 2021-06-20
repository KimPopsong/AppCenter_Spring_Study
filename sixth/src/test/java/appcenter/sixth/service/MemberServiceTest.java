package appcenter.sixth.service;

import appcenter.sixth.domain.Member;
import appcenter.sixth.model.MemberCreateRequest;
import appcenter.sixth.model.MemberUpdateRequest;
import appcenter.sixth.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks  // memberRepository를 memberService에 주입
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;  // 가짜 객체

    @Test
    @DisplayName("Save Member")
    public void saveMember() {

        // given
        MemberCreateRequest memberCreateRequest = new MemberCreateRequest();
        memberCreateRequest.setName("김대성");
        memberCreateRequest.setAge(20);

        Member member = Member.createMember(1L, "김대성", 20);

        given(memberRepository.save(any()))
                .willReturn(member);

        // when
        Member saveMember = memberService.saveMember(memberCreateRequest);

        // then
        assertThat(saveMember.getId()).isEqualTo(1L);
        assertThat(saveMember.getName()).isEqualTo(memberCreateRequest.getName());
        assertThat(saveMember.getAge()).isEqualTo(memberCreateRequest.getAge());

        then(memberRepository).should(times(1)).save(any());
    }

    @Test
    @DisplayName("Update member")
    public void updateMember() {

        // given
        MemberUpdateRequest memberUpdateRequest = new MemberUpdateRequest();
        memberUpdateRequest.setName("김대성");
        memberUpdateRequest.setAge(23);

        Member member = Member.createMember(1L, "ㄱㄷㅅ", 231);
        Member member2 = Member.createMember(1L, "ㄱㄷㅅ", 232);

        given(memberRepository.findById(any()))
                .willReturn(Optional.of(member));

        // when
        Member updateMember = memberService.updateMember(1L, memberUpdateRequest);

        // then
        assertThat(updateMember.getId()).isEqualTo(1L);
        assertThat(updateMember.getName()).isEqualTo(memberUpdateRequest.getName());
        assertThat(updateMember.getAge()).isEqualTo(memberUpdateRequest.getAge());

        then(memberRepository).should(times(1)).findById(any());
    }

    @Test
    @DisplayName("Delete Member")
    public void deleteMember() {

        // given
        Member member = Member.createMember(1L, "김대성", 23);

        given(memberRepository.findById(any()))
                .willReturn(Optional.of(member));

        // when
        boolean result = memberService.deleteMember(1L);

        // then
        assertThat(result).isEqualTo(true);

        then(memberRepository).should(times(1)).findById(any());
    }
}