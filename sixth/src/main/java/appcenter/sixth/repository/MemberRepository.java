package appcenter.sixth.repository;

import appcenter.sixth.domain.Member;
import appcenter.sixth.domain.MemberStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByStatus(MemberStatus status);
}
