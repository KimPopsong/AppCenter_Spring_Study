package com.example.third.controller;

import com.example.third.domain.Team;
import com.example.third.model.team.TeamResponse;
import com.example.third.model.team.TeamSaveRequest;
import com.example.third.model.team.TeamUpdateRequest;
import com.example.third.service.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @PostMapping("/teams")
    public ResponseEntity saveTeam(@RequestBody TeamSaveRequest teamSaveRequest) {
        teamService.saveTeam(teamSaveRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/teams/{teamId}")
    public ResponseEntity updateTeam(@PathVariable Long teamId, @RequestBody TeamUpdateRequest teamUpdateRequest) {
        teamService.updateTeam(teamId, teamUpdateRequest);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/teams/{teamId}")
    public ResponseEntity deleteTeam(@PathVariable Long teamId) {
        teamService.deleteTeam(teamId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/teams")
    public List<TeamResponse> findAllTeam() {
        List<Team> teams = teamService.findAll();
        List<TeamResponse> teamResponses = teams.stream().map(TeamResponse::new).collect(Collectors.toList());

        return teamResponses;
    }

    @GetMapping("/teams/{teamId}")
    public TeamResponse findById(@PathVariable Long teamId){
        Team team = teamService.findById(teamId);

        return new TeamResponse(team);
    }
}
