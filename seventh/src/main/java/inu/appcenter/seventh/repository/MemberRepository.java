package inu.appcenter.seventh.repository;

import inu.appcenter.seventh.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
