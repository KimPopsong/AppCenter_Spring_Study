package com.example.fifth.dto;

import com.example.fifth.domain.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberWithTodoCount {

    private Member member;

    private Long count;
}
