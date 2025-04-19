package users_ies_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import users_ies_project.entity.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {
} 