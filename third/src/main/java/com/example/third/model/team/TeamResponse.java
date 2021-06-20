package com.example.third.model.team;

import com.example.third.domain.Team;
import com.example.third.model.member.MemberDTO;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class TeamResponse {

    private Long teamId;

    private String name;

    private List<MemberDTO> members;

    public TeamResponse(Team team) {
        this.teamId = team.getId();
        this.name = team.getName();
        this.members = team.getMemberList().stream().map(MemberDTO::new).collect(Collectors.toList());
    }
}
