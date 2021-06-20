package com.example.fifth.model.member;

import com.example.fifth.domain.Member;
import com.example.fifth.domain.MemberStatus;
import com.example.fifth.model.todo.TodoResponse;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class MemberResponse {

    private Long memberId;

    private String email;

    private String name;

    private MemberStatus status;

    List<TodoResponse> todoList;

    public static MemberResponse from(Member member) {
        MemberResponse memberResponse = new MemberResponse();

        memberResponse.memberId = member.getId();
        memberResponse.email = member.getEmail();
        memberResponse.name = member.getName();
        memberResponse.status = member.getStatus();
        memberResponse.todoList = member.getTodoList().stream().map(TodoResponse::from).collect(Collectors.toList());

        return memberResponse;
    }
}
