package org.techwork.verycool.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "comments")
@NoArgsConstructor
@AllArgsConstructor
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference("idea-comments")  // Nom unique pour la référence
    @ManyToOne
    @JoinColumn(name = "idea_id", nullable = false)
    private IdeaEntity idea;

    @JsonBackReference("user-comments")  // Nom unique pour la référence
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(length = 5000)
    private String content;

    @CreationTimestamp
    private Date createdAt;
}