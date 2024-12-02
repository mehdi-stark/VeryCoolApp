package org.techwork.verycool.models.dto;

import lombok.Data;
import org.techwork.verycool.models.entities.CommentEntity;
import org.techwork.verycool.models.entities.IdeaEntity;

import java.util.Date;
import java.util.List;

@Data
public class User {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String fullName;
    private String role;
    private List<IdeaEntity> ideas;
    private List<CommentEntity> comments;
    private String createdBy;
    private String updatedBy;
    private Date createdAt;
    private Date updatedAt;
}
