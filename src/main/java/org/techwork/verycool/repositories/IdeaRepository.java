package org.techwork.verycool.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.techwork.verycool.models.entities.IdeaEntity;
import org.techwork.verycool.models.entities.UserEntity;

import java.util.List;

@Repository
public interface IdeaRepository extends JpaRepository<IdeaEntity, Long> {
    List<IdeaEntity> findAllByUser(UserEntity user);
    List<IdeaEntity> findByUserId(Long userId);
}
