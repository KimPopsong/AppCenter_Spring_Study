package inu.appcenter.finalterm.repository;

import inu.appcenter.finalterm.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
