package users_ies_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import users_ies_project.entity.SecurityModuleUser;

public interface SecurityModuleUserRepository extends JpaRepository<SecurityModuleUser, Integer> {
} 