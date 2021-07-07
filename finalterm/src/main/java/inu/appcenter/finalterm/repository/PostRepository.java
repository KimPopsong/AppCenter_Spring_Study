package inu.appcenter.finalterm.repository;

import inu.appcenter.finalterm.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
