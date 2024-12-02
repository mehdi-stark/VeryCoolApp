package org.techwork.verycool.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.techwork.verycool.constants.Constants;
import org.techwork.verycool.models.entities.CommentEntity;
import org.techwork.verycool.services.CommentService;
import org.techwork.verycool.utils.MapperUtils;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/" + Constants.VERSION)
@Tag(name = "Comments Controller", description = "API for managing comments")
public class CommentsController {

    final CommentService commentService;

    public CommentsController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Operation(summary = "Get all comments", description = "Retrieve a list of all comments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = CommentEntity.class))),
            @ApiResponse(responseCode = "204", description = "No content")
    })
    @GetMapping("/comments")
    public ResponseEntity<?> getComments() {
        log.info("Enter getComments");
        List<CommentEntity> commentEntityList = commentService.findAll();
        return commentEntityList.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(commentEntityList);
    }

    @Operation(summary = "Get comments by user ID", description = "Retrieve a list of comments for a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = CommentEntity.class))),
            @ApiResponse(responseCode = "204", description = "No content")
    })
    @GetMapping("/comments/user/{userId}")
    public ResponseEntity<?> getUserComments(@Parameter(description = "User ID") @PathVariable Long userId) {
        log.info("Enter user Comments");
        List<CommentEntity> ideaEntityList = commentService.findUserComment(userId);
        return ideaEntityList.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(ideaEntityList);
    }

    @Operation(summary = "Save a new comment", description = "Save a new comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = CommentEntity.class)))
    })
    @PostMapping("/comment")
    public ResponseEntity<?> saveComment(@RequestBody CommentEntity comment) {
        log.info("Save Comment Request : {}", MapperUtils.writeValueAsString(comment));
        CommentEntity savedComment = commentService.saveComment(comment);
        return ResponseEntity.ok(savedComment);
    }

    @Operation(summary = "Delete a comment", description = "Delete a comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation")
    })
    @DeleteMapping("/comment")
    public ResponseEntity<?> deleteComment(@RequestBody CommentEntity comment) {
        log.info("Delete Comment Request : {}", MapperUtils.writeValueAsString(comment));
        commentService.deleteComment(comment);
        return ResponseEntity.ok().build();
    }
}
