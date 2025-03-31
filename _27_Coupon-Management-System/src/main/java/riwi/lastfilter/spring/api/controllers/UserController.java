package riwi.lastfilter.spring.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import riwi.lastfilter.spring.api.dto.request.UserRequest;
import riwi.lastfilter.spring.api.dto.response.UserResponse;
import riwi.lastfilter.spring.infrastructure.abstrasct_services.IUserService;
import riwi.lastfilter.spring.infrastructure.services.UserService;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
@Tag(name = "Users")
public class UserController 
{
    @Autowired
    private final IUserService IUserService;

    @ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(mediaType = "application/json"/* , schema = @Schema(implementation = ErrorsResp.class)*/)),
        @ApiResponse(responseCode = "200", description = "Success operation", content = @Content(mediaType = "application/json"))})
    @Operation(summary = "Create a new user", description = "Send information to create a new user")
    @PostMapping
    public ResponseEntity<UserResponse> save(@Validated @RequestBody UserRequest user) {
        return ResponseEntity.ok(this.IUserService.create(user));
    }

}
