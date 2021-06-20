package com.example.third.repository;

import com.example.third.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {

    //"select t from Team t where t.name=:name"
    Optional<Team> findByName(String name);

    @Query("select t From Team t join fetch t.memberList where t.id =:teamId")
    public Optional<Team> findWithMemberById(@Param("teamId") Long teamId);
}
