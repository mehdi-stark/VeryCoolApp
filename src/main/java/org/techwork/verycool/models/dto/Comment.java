package org.techwork.verycool.models.dto;

import lombok.Data;

import java.util.Date;

@Data
public class Comment {
    private Long id;
    private Long ideaId;
    private Long userId;
    private String content;
    private Date createdAt;
}