package org.techwork.verycool.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.techwork.verycool.models.entities.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
