package com.example.third.model.member;

import com.example.third.domain.Member;
import lombok.Data;

import java.io.Serializable;

@Data
public class MemberDTO implements Serializable {

    private Long memberId;
    private String email;
    private String name;
    private int age;

    public MemberDTO(Member member) {
        this.memberId = member.getId();
        this.email = member.getEmail();
        this.name = member.getName();
        this.age = member.getAge();
    }
}
