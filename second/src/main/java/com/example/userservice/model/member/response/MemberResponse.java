package com.example.userservice.model.member.response;

import com.example.userservice.domain.Member;
import lombok.Data;

@Data
public class MemberResponse {

    private Long id;
    private String name;
    private Integer age;

    public MemberResponse(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.age = member.getAge();
    }
}
