package org.techwork.verycool.models.dto;

import lombok.Data;
import org.techwork.verycool.models.entities.CommentEntity;

import java.util.Date;
import java.util.List;

@Data
public class Idea {
    private Long id;
    private String title;
    private String description;
    private int nbLikes;
    private String mediaUrl;
    private Long userId;
    private List<CommentEntity> comments;
    private Date createdAt;
    private Date updatedAt;
}