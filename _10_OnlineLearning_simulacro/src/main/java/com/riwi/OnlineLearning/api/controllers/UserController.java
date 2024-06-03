package com.riwi.OnlineLearning.api.controllers;

import com.riwi.OnlineLearning.api.dto.request.UserReq;
import com.riwi.OnlineLearning.api.dto.response.UserResp;
import com.riwi.OnlineLearning.infraestructure.abstract_services.IUserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Users")
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final IUserService userService;

    @Operation(summary = "Create a new user", description = "Endpoint to create a new user")
    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    @PostMapping
    public ResponseEntity<UserResp> register(@Validated @RequestBody UserReq request) {
        return ResponseEntity.ok(this.userService.create(request));
    }

    @Operation(summary = "Get user information", description = "Endpoint to get detailed information of a specific user")
    @ApiResponse(responseCode = "400", description = "Invalid user ID", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    @GetMapping("/{user_id}")
    public ResponseEntity<UserResp> getUserInfo(@PathVariable("user_id") Long userId) {
        return ResponseEntity.ok(this.userService.get(userId));
    }

    @Operation(summary = "Update user information", description = "Endpoint to update user information")
    @ApiResponse(responseCode = "400", description = "Invalid user ID", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    @PutMapping("/update/{user_id}")
    public ResponseEntity<UserResp> updateUser(@PathVariable("user_id") Long userId,
                                               @Validated @RequestBody UserReq request) {
        return ResponseEntity.ok(this.userService.update(request, userId));
    }

    @Operation(summary = "Delete a user", description = "Endpoint to delete a user")
    @ApiResponse(responseCode = "400", description = "Invalid user ID", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    @DeleteMapping("/delete/{user_id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("user_id") Long userId) {
        this.userService.delete(userId);
        return ResponseEntity.noContent().build();
    }
}

