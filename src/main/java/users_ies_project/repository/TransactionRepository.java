package users_ies_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import users_ies_project.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
} 