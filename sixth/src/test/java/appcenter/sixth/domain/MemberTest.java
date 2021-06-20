package appcenter.sixth.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MemberTest {

    /**
     * TDD -> 테스트 주도 개발
     * <p>
     * 1. 실패하는 테스트 작성
     * 2. 테스트 실행 -> 테스트가 깨짐
     * 3. 테스트의 깨진 부분을 보완
     * 4. 다시 테스트 실행 -> 테스트 성공
     *
     * given 어떠한 환경에서
     * when 이러한 행동을 하였을 때
     * then 이러한 결과가 나올 것이다
     */

    @BeforeEach  // 테스트 시작 전 실행
    public void init() {
        // Member member = Member.createMember("김대성", 23);
    }

    @Test
    @DisplayName("Create Member")
    public void createMember() {

        // given

        // when
        Member member = Member.createMember("김대성", 23);

        // then
        assertThat(member.getName()).isEqualTo("김대성");
        assertThat(member.getAge()).isEqualTo(23);
        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVATED);
    }

    @Test
    @DisplayName("Update Member")
    public void updateMember() {

        // given
        Member member = Member.createMember("김대성", 23);

        // when
        member.update("김대성대성", 230);

        // then
        assertThat(member.getName()).isEqualTo("김대성대성");
        assertThat(member.getAge()).isEqualTo(230);
    }

    @Test
    @DisplayName("Delete Member")
    public void deleteMember() {

        // given
        Member member = Member.createMember("김대성", 23);

        // when
        member.delete();

        // then
        assertThat(member.getStatus()).isEqualTo(MemberStatus.DELETED);
    }
}