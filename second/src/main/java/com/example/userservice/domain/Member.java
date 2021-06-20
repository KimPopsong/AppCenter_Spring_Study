package com.example.userservice.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;

    private int age;

    public static Member createMember(String name, int age){
        Member member = new Member();

        member.name = name;
        member.age = age;

        return member;
    }

    public void changeMember(String name, int age){
        this.name = name;
        this.age = age;
    }
}
