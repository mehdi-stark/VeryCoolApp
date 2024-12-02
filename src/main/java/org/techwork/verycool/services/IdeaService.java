package org.techwork.verycool.services;

import org.techwork.verycool.models.entities.IdeaEntity;

import java.util.List;

public interface IdeaService {
    List<IdeaEntity> findIdeas();
    IdeaEntity findOne(Long id);
    List<IdeaEntity> findUserIdeas(Long idUser);
    IdeaEntity saveIdea(IdeaEntity idea);
    void deleteIdea(IdeaEntity idea);

    IdeaEntity like(Long ideaId);
    IdeaEntity unlike(Long ideaId);
}
