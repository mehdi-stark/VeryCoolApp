package org.techwork.verycool.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.techwork.verycool.constants.Constants;
import org.techwork.verycool.models.entities.IdeaEntity;
import org.techwork.verycool.services.IdeaService;
import org.techwork.verycool.utils.MapperUtils;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/" + Constants.VERSION)
@Tag(name = "Ideas Controller", description = "API for managing ideas")
public class IdeasController {

    final IdeaService ideaService;

    public IdeasController(IdeaService ideaService) {
        this.ideaService = ideaService;
    }

    @Operation(summary = "Find all ideas", description = "Retrieve a list of all ideas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = IdeaEntity.class))),
            @ApiResponse(responseCode = "204", description = "No content")
    })
    @GetMapping("/ideas")
    public ResponseEntity<?> findIdeas() {
        List<IdeaEntity> ideaEntityList = ideaService.findIdeas();
        return ideaEntityList.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(ideaEntityList);
    }

    @Operation(summary = "Find ideas by user ID", description = "Retrieve a list of ideas for a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = IdeaEntity.class))),
            @ApiResponse(responseCode = "204", description = "No content")
    })
    @GetMapping("/ideas/user/{userId}")
    public ResponseEntity<?> findUserIdeas(@Parameter(description = "User ID") @PathVariable Long userId) {
        log.debug("Find User Idea Request : {}", userId);
        List<IdeaEntity> ideaEntityList = ideaService.findUserIdeas(userId);
        return ideaEntityList.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(ideaEntityList);
    }

    @Operation(summary = "Save a new idea", description = "Save a new idea")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = IdeaEntity.class)))
    })
    @PostMapping("/idea")
    public ResponseEntity<?> saveIdea(@RequestBody IdeaEntity idea) {
        log.debug("Save Idea Request : {}", MapperUtils.writeValueAsString(idea));
        IdeaEntity savedIdea = ideaService.saveIdea(idea);
        return ResponseEntity.ok(savedIdea);
    }

    @Operation(summary = "Like an idea", description = "Add a like to an idea")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = IdeaEntity.class)))
    })
    @PatchMapping("/idea/{ideaId}/like")
    public ResponseEntity<?> likeIdea(@Parameter(description = "Idea ID") @PathVariable Long ideaId) {
        log.debug("Add like Request : {}", ideaId);
        IdeaEntity savedIdea = ideaService.like(ideaId);
        return ResponseEntity.ok(savedIdea);
    }

    @Operation(summary = "Unlike an idea", description = "Remove a like from an idea")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = IdeaEntity.class)))
    })
    @PatchMapping("/idea/{ideaId}/unlike")
    public ResponseEntity<?> unlikeIdea(@Parameter(description = "Idea ID") @PathVariable Long ideaId) {
        log.debug("Add unlike Request : {}", ideaId);
        IdeaEntity savedIdea = ideaService.unlike(ideaId);
        return ResponseEntity.ok(savedIdea);
    }

    @Operation(summary = "Delete an idea", description = "Delete an idea (admin only)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation")
    })
    @RolesAllowed("admin")
    @DeleteMapping("/idea")
    public ResponseEntity<?> deleteIdea(@RequestBody IdeaEntity idea) {
        log.debug("Delete Idea Request : {}", MapperUtils.writeValueAsString(idea));
        ideaService.deleteIdea(idea);
        return ResponseEntity.ok("Idea has been deleted successfully");
    }
}
