package com.example.fifth.repository;

import com.example.fifth.domain.Member;
import com.example.fifth.domain.Todo;
import com.example.fifth.dto.MemberWithTodoCount;
import com.example.fifth.repository.query.MemberQueryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

//@DataJpaTest  // 데이터 엑세스 계층의 빈들을 가져옴. EntityManager, DataSource, @Repository 빈들을 가져옴
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)  // H2 내장 DB로 테스트
@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private MemberQueryRepository memberQueryRepository;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("회원 저장 테스트")
    void Test() {
        Member member = Member.createMember("abcde@naver.com", "1234", "HAHA");

        Member savedMember = memberRepository.save(member);

        assertNotNull(savedMember.getId());  // May Not Null...
    }

    // 회원 - 글, 글의 상태가 DELETE가 아닌 것
    @Test
    @DisplayName("회원 - todo 같이 조회")
    void findMemberById() {
        Member member = Member.createMember("abcde@naver.com", "1234", "HAHA");

        memberRepository.save(member);

        Todo todo1 = Todo.createTodo("AppCenterStudy", member);
        Todo todo2 = Todo.createTodo("DBStudy", member);
        Todo todo3 = Todo.createTodo("ServerStudy", member);
        todo3.changeStatus();

        todoRepository.save(todo1);
        todoRepository.save(todo2);
        todoRepository.save(todo3);

        em.flush();  // 영속성 컨텍스트에 있는 쿼리 날아감
        em.clear();  // 영속성 컨텍스트 초기화

        Member findMember = memberQueryRepository.findMemberById(1L);

        assertThat(findMember.getTodoList().size()).isEqualTo(2);
    }

    // 글을 조회하면서 글의 댓글 수를 찾아오는 방법
    // 회원을 조회하면서 Todo의 개수를 조회
    @Test
    @DisplayName("회원 조회 - todo 개수")
    void findMemberWithTodoCountsById(){
        Member member = Member.createMember("abcde@naver.com", "1234", "HAHA");

        memberRepository.save(member);

        Todo todo1 = Todo.createTodo("AppCenterStudy", member);
        Todo todo2 = Todo.createTodo("DBStudy", member);
        Todo todo3 = Todo.createTodo("ServerStudy", member);
        todo3.changeStatus();

        todoRepository.save(todo1);
        todoRepository.save(todo2);
        todoRepository.save(todo3);

        em.flush();  // 영속성 컨텍스트에 있는 쿼리 날아감
        em.clear();  // 영속성 컨텍스트 초기화

        MemberWithTodoCount result = memberQueryRepository.findMemberWithTodoCountsById(1L);

        assertThat(result.getCount()).isEqualTo(3);
    }
}