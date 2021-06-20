package com.example.third.service;

import com.example.third.domain.Team;
import com.example.third.exception.TeamException;
import com.example.third.model.team.TeamSaveRequest;
import com.example.third.model.team.TeamUpdateRequest;
import com.example.third.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    @Transactional
    public void saveTeam(TeamSaveRequest teamSaveRequest) {
        validatedDuplicatedTeamName(teamSaveRequest.getName());

        Team team = Team.createTeam(teamSaveRequest.getName());
        teamRepository.save(team);
    }

    @Transactional
    public void updateTeam(Long teamId, TeamUpdateRequest teamUpdateRequest) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new TeamException("No Team Exists"));

        validatedDuplicatedTeamName(teamUpdateRequest.getName());

        team.changeName(teamUpdateRequest.getName());
    }

    @Transactional
    public void deleteTeam(Long teamId) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new TeamException("No Team Exists"));

        teamRepository.delete(team);
    }

    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    public Team findById(Long teamId) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new TeamException("No Team Exists"));

        return team;
    }

    private void validatedDuplicatedTeamName(String name) {
        Optional<Team> result = teamRepository.findByName(name);

        if (result.isPresent()) {
            throw new TeamException("request teamName is duplicated");
        }
    }
}
