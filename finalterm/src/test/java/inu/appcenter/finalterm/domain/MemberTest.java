package inu.appcenter.finalterm.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MemberTest {

    @Test
    @DisplayName("회원 가입")
    public void saveMember() {

        Member member = Member.createMember("kimds5344@naver.com", "abcd", "DSKIM");

        assertThat(member.getEmail()).isEqualTo("kimds5344@naver.com");
        assertThat(member.getPassword()).isEqualTo("abcd");
        assertThat(member.getNickName()).isEqualTo("DSKIM");
    }
}