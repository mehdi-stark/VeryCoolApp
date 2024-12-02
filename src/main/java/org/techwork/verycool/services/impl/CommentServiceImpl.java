package org.techwork.verycool.services.impl;

import org.springframework.stereotype.Service;
import org.techwork.verycool.models.entities.CommentEntity;
import org.techwork.verycool.repositories.CommentRepository;
import org.techwork.verycool.services.CommentService;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public List<CommentEntity> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public CommentEntity findOne(Long id) {
        return commentRepository.findById(id).orElse(null);
    }

    @Override
    public List<CommentEntity> findUserComment(Long userId) {
        return commentRepository.findAllByUserId(userId);
    }

    @Override
    public CommentEntity saveComment(CommentEntity comment) {
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(CommentEntity comment) {
        commentRepository.delete(comment);
    }
}
