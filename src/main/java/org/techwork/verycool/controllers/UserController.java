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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.techwork.verycool.constants.Constants;
import org.techwork.verycool.models.entities.UserEntity;
import org.techwork.verycool.services.UserService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/" + Constants.VERSION)
@Tag(name = "User Controller", description = "API for managing users")
public class UserController {

    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Find all users", description = "Retrieve a list of all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = UserEntity.class))),
            @ApiResponse(responseCode = "204", description = "No content")
    })
    @GetMapping("/users")
    public ResponseEntity<?> findUsers() {
        log.info("Enter get users");
        List<UserEntity> users = userService.findUsers();
        return users.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(users);
    }

    @Operation(summary = "Find user by ID", description = "Retrieve a user by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = UserEntity.class))),
            @ApiResponse(responseCode = "204", description = "No content")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> findUserById(@Parameter(description = "User ID") @PathVariable Long userId) {
        log.info("Enter get user by ID");
        UserEntity user = userService.findUserById(userId);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.noContent().build();
    }
}
