package org.techwork.verycool.services;

import org.techwork.verycool.models.entities.CommentEntity;

import java.util.List;

public interface CommentService {
    List<CommentEntity> findAll();
    CommentEntity findOne(Long id);
    List<CommentEntity> findUserComment(Long id);
    CommentEntity saveComment(CommentEntity comment);

    void deleteComment(CommentEntity comment);
}