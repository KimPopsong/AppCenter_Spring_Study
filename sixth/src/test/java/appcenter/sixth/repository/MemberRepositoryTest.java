package appcenter.sixth.repository;

import appcenter.sixth.domain.Member;
import appcenter.sixth.domain.MemberStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

//@SpringBootTest  // 통합 테스트
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)  // 내장 DB. 메모리
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)  // 실제 DB
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @Transactional  // 테스트코드의 @Transactional은 테스트 코드 실행 후 롤백.
    @DisplayName("Save Member")
    public void saveMember() {

        // given
        Member member = Member.createMember("김대성", 23);

        // when
        Member savedMember = memberRepository.save(member);

        // then
        assertNotNull(savedMember.getId());
    }

    @Test
    @DisplayName("Find One Member")
    public void findMember() {

        // given
        Member member = Member.createMember("김대성", 23);
        Member savedMember = memberRepository.save(member);

        // when
        Member findMember = memberRepository.findById(savedMember.getId()).get();

        // then
        assertThat(findMember.getId()).isEqualTo(savedMember.getId());
        assertThat(findMember.getName()).isEqualTo(savedMember.getName());
        assertThat(findMember.getAge()).isEqualTo(savedMember.getAge());
    }

    @Test
    @DisplayName("Find Member List")
    public void findMembers() {

        // given
        Member member1 = Member.createMember("김대성1", 231);
        Member member2 = Member.createMember("김대성2", 232);
        Member member3 = Member.createMember("김대성3", 233);

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

        // when
        List<Member> result = memberRepository.findAll();

        // then
        assertThat(result.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("Find Deleted Member")
    public void findDeletedMember() {

        // given
        Member member1 = Member.createMember("김대성1", 231);
        Member member2 = Member.createMember("김대성2", 232);
        Member member3 = Member.createMember("김대성3", 233);

        member3.delete();

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

        // when

        Member findMember = memberRepository.findByStatus(MemberStatus.DELETED).get();

        // then
        assertThat(findMember.getName()).isEqualTo("김대성3");
    }
}