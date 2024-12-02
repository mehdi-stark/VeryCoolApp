package org.techwork.verycool.services.impl;

import org.springframework.stereotype.Service;
import org.techwork.verycool.exceptions.IdeaNotFoundException;
import org.techwork.verycool.models.entities.IdeaEntity;
import org.techwork.verycool.repositories.IdeaRepository;
import org.techwork.verycool.services.IdeaService;

import java.util.List;

@Service
public class IdeaServiceImpl implements IdeaService {

    final IdeaRepository ideaRepository;

    public IdeaServiceImpl(IdeaRepository ideaRepository) {
        this.ideaRepository = ideaRepository;
    }

    @Override
    public List<IdeaEntity> findIdeas() {
        return ideaRepository.findAll();
    }

    @Override
    public IdeaEntity findOne(Long id) {
        return ideaRepository.findById(id).orElseThrow(() -> new IdeaNotFoundException("Idea not found or does not exist"));
    }

    @Override
    public List<IdeaEntity> findUserIdeas(Long userId) {
        return ideaRepository.findByUserId(userId);
    }

    @Override
    public IdeaEntity saveIdea(IdeaEntity idea) {
        return ideaRepository.save(idea);
    }

    @Override
    public void deleteIdea(IdeaEntity idea) {
        IdeaEntity ideaToDelete = findOne(idea.getId());
        ideaRepository.delete(ideaToDelete);
    }

    @Override
    public IdeaEntity like(Long ideaId) {
        IdeaEntity idea = findOne(ideaId);
        idea.addLike();
        return ideaRepository.save(idea);
    }

    @Override
    public IdeaEntity unlike(Long ideaId) {
        IdeaEntity idea = findOne(ideaId);
        idea.removeLike();
        return ideaRepository.save(idea);
    }
}