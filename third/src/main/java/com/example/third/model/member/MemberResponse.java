package com.example.third.model.member;

import com.example.third.domain.Member;
import com.example.third.model.team.TeamResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class MemberResponse {

    private Long memberId;
    private String email;
    private String name;
    private int age;

    @JsonIgnore
    private TeamResponse team;

    public MemberResponse(Member member) {
        this.memberId = member.getId();
        this.email = member.getEmail();
        this.name = member.getName();
        this.age = member.getAge();
        this.team = new TeamResponse(member.getTeam());
    }
}
