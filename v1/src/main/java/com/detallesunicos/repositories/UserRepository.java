package com.detallesunicos.v1.repositories;

import com.detallesunicos.v1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Métodos CRUD listos para usar
}
