package com.example.fifth.service;

import com.example.fifth.domain.Member;
import com.example.fifth.domain.Todo;
import com.example.fifth.model.todo.TodoSaveRequest;
import com.example.fifth.repository.MemberRepository;
import com.example.fifth.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoService {

    private final TodoRepository todoRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void saveTodo(String email, TodoSaveRequest request) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(IllegalStateException::new);

        Todo todo = Todo.createTodo(request.getContent(), member);

        todoRepository.save(todo);
    }
}
