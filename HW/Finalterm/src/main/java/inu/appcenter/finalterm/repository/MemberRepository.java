package inu.appcenter.finalterm.repository;

import inu.appcenter.finalterm.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select distinct m from Member m left join fetch m.roles where m.id=:memberId")
    Optional<Member> findWithRolesById(@Param("memberId") long parseLong);

    Optional<Member> findByEmail(String email);
}
