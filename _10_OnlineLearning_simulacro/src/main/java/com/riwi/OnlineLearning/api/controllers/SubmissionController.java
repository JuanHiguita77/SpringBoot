package com.riwi.OnlineLearning.api.controllers;

import com.riwi.OnlineLearning.api.dto.errors.ErrorsResp;
import com.riwi.OnlineLearning.api.dto.request.SubmissionReq;
import com.riwi.OnlineLearning.api.dto.response.SubmissionResp;
import com.riwi.OnlineLearning.infraestructure.abstract_services.ISubmissionService;

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

@Tag(name = "Submissions")
@RestController
@RequestMapping("/submissions")
@AllArgsConstructor
public class SubmissionController {

    private final ISubmissionService submissionService;

    @Operation(summary = "Create a new submission", description = "Endpoint to create a new submission for an assignment")
    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResp.class)))
    @PostMapping
    public ResponseEntity<SubmissionResp> createSubmission(@Validated @RequestBody SubmissionReq request) {
        return ResponseEntity.ok(this.submissionService.create(request));
    }

    @Operation(summary = "Get submission information", description = "Endpoint to get detailed information of a specific submission")
    @ApiResponse(responseCode = "400", description = "Invalid submission ID", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResp.class)))
    @GetMapping("/{submission_id}")
    public ResponseEntity<SubmissionResp> getSubmissionInfo(@PathVariable("submission_id") Long submissionId) {
        return ResponseEntity.ok(this.submissionService.get(submissionId));
    }

    @Operation(summary = "Update submission information", description = "Endpoint to update submission information")
    @ApiResponse(responseCode = "400", description = "Invalid submission ID", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResp.class)))
    @PutMapping("/update/{submission_id}")
    public ResponseEntity<SubmissionResp> updateSubmission(@PathVariable("submission_id") Long submissionId,
                                                           @Validated @RequestBody SubmissionReq request) {
        return ResponseEntity.ok(this.submissionService.update(request, submissionId));
    }

    @Operation(summary = "Delete a submission", description = "Endpoint to delete a submission")
    @ApiResponse(responseCode = "400", description = "Invalid submission ID", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResp.class)))
    @DeleteMapping("/delete/{submission_id}")
    public ResponseEntity<Void> deleteSubmission(@PathVariable("submission_id") Long submissionId) {
        this.submissionService.delete(submissionId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get submissions by assignment ID", description = "Endpoint to get all submissions of a specific assignment")
    @ApiResponse(responseCode = "400", description = "Invalid assignment ID", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResp.class)))
    @GetMapping("/assignment/{assignment_id}/submissions")
    public ResponseEntity<List<SubmissionResp>> getSubmissionsByAssignment(@PathVariable("assignment_id") Long assignmentId) {
        return ResponseEntity.ok(this.submissionService.getSubmissionsByAssignmentId(assignmentId));
    }

    @Operation(summary = "Get submissions by user ID", description = "Endpoint to get all submissions of a specific user")
    @ApiResponse(responseCode = "400", description = "Invalid user ID", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResp.class)))
    @GetMapping("/user/{user_id}/submissions")
    public ResponseEntity<List<SubmissionResp>> getSubmissionsByUser(@PathVariable("user_id") Long userId) {
        return ResponseEntity.ok(this.submissionService.getSubmissionsByUserId(userId));
    }
}

