package com.riwi.OnlineLearning.api.controllers;

import com.riwi.OnlineLearning.api.dto.errors.ErrorsResp;
import com.riwi.OnlineLearning.api.dto.request.AssignmentReq;
import com.riwi.OnlineLearning.api.dto.response.AssignmentResp;
import com.riwi.OnlineLearning.infraestructure.abstract_services.IAssignmentService;

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

@Tag(name = "Assignments")
@RestController
@RequestMapping("/assignments")
@AllArgsConstructor
public class AssignmentController {

    private final IAssignmentService assignmentService;

    @Operation(summary = "Create a new assignment", description = "Endpoint to create a new assignment")
    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResp.class)))
    @PostMapping
    public ResponseEntity<AssignmentResp> createAssignment(@Validated @RequestBody AssignmentReq request) {
        return ResponseEntity.ok(this.assignmentService.create(request));
    }

    @Operation(summary = "Get assignment information", description = "Endpoint to get detailed information of a specific assignment")
    @ApiResponse(responseCode = "400", description = "Invalid assignment ID", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResp.class)))
    @GetMapping("/{assignment_id}")
    public ResponseEntity<AssignmentResp> getAssignmentInfo(@PathVariable("assignment_id") Long assignmentId) {
        return ResponseEntity.ok(this.assignmentService.get(assignmentId));
    }

    @Operation(summary = "Update assignment information", description = "Endpoint to update assignment information")
    @ApiResponse(responseCode = "400", description = "Invalid assignment ID", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResp.class)))
    @PutMapping("/update/{assignment_id}")
    public ResponseEntity<AssignmentResp> updateAssignment(@PathVariable("assignment_id") Long assignmentId,
                                                           @Validated @RequestBody AssignmentReq request) {
        return ResponseEntity.ok(this.assignmentService.update(request, assignmentId));
    }

    @Operation(summary = "Delete an assignment", description = "Endpoint to delete an assignment")
    @ApiResponse(responseCode = "400", description = "Invalid assignment ID", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResp.class)))
    @DeleteMapping("/delete/{assignment_id}")
    public ResponseEntity<Void> deleteAssignment(@PathVariable("assignment_id") Long assignmentId) {
        this.assignmentService.delete(assignmentId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get assignments by lesson ID", description = "Endpoint to get all assignments of a specific lesson")
    @ApiResponse(responseCode = "400", description = "Invalid lesson ID", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResp.class)))
    @GetMapping("/lesson/{lesson_id}/assignments")
    public ResponseEntity<List<AssignmentResp>> getAssignmentsByLesson(@PathVariable("lesson_id") Long lessonId) {
        return ResponseEntity.ok(this.assignmentService.getAssignmentsByLessonId(lessonId));
    }
}

