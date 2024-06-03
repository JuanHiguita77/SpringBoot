package com.riwi.OnlineLearning.api.controllers;

import com.riwi.OnlineLearning.api.dto.errors.ErrorsResp;
import com.riwi.OnlineLearning.api.dto.request.MessageReq;
import com.riwi.OnlineLearning.api.dto.response.MessageResp;
import com.riwi.OnlineLearning.infraestructure.abstract_services.IMessageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Messages")
@RestController
@RequestMapping("/messages")
@AllArgsConstructor
public class MessageController {

    private final IMessageService messageService;

    @Operation(summary = "Send a message", description = "Endpoint to send a message from one user to another within a course")
    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResp.class)))
    @PostMapping(path = "/send")
    public ResponseEntity<MessageResp> sendMessage(@Validated @RequestBody MessageReq request) {
        return ResponseEntity.ok(this.messageService.create(request));
    }

    @Operation(summary = "Get messages by course ID", description = "Endpoint to get all messages of a specific course")
    @ApiResponse(responseCode = "400", description = "Invalid course ID", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResp.class)))
    @GetMapping("/course/{course_id}")
    public ResponseEntity<List<MessageResp>> getMessagesByCourse(@PathVariable("course_id") Long courseId) {
        return ResponseEntity.ok(this.messageService.getMessagesByCourseId(courseId));
    }

    @Operation(summary = "Get messages between users", description = "Endpoint to get all messages between two specific users")
    @ApiResponse(responseCode = "400", description = "Invalid user IDs", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResp.class)))
    @GetMapping
    public ResponseEntity<List<MessageResp>> getMessagesBetweenUsers(@RequestParam("sender_id") Long senderId,
                                                                     @RequestParam("receiver_id") Long receiverId) {
        return ResponseEntity.ok(this.messageService.getMessagesBetweenUsers(senderId, receiverId));
    }
}

