package com.riwi.librosYa.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.riwi.librosYa.api.dto.DTOUser;
import com.riwi.librosYa.infraestructure.abstract_Services.IUserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
@Tag(name = "Users")
public class UserController 
{
    @Autowired
    private final IUserService userService;

    @Operation(summary = "Create a new user", description = "Send information to create a new user")
    @PostMapping
    public ResponseEntity<DTOUser> save(@Validated @RequestBody DTOUser user) {
        return ResponseEntity.ok(this.userService.create(user));
    }

    @Operation(summary = "Delete a user by id", description = "Send the user id to delete it")
    @DeleteMapping(path = "/{user_id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        this.userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update a user", description = "Send information to update a user")
    @PutMapping(path = "/{user_id}")
    public ResponseEntity<DTOUser> update(@PathVariable Long id, @Validated @RequestBody DTOUser user) {
        return ResponseEntity.ok(this.userService.update(user, id));
    }

    @Operation(summary = "Get a user by id", description = "Send the user id to get user details")
    @GetMapping("/{user_id}")
    public ResponseEntity<DTOUser> findById(@PathVariable Long id) {
        return ResponseEntity.ok(this.userService.get(id));
    }
}
