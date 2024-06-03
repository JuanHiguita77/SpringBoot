package com.riwi.OnlineLearning.api.controllers;

import com.riwi.OnlineLearning.api.dto.errors.ErrorsResp;
import com.riwi.OnlineLearning.api.dto.request.EnrollmentReq;
import com.riwi.OnlineLearning.api.dto.response.EnrollmentResp;
import com.riwi.OnlineLearning.infraestructure.abstract_services.IEnrollmentService;

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

@Tag(name = "Enrollments")
@RestController
@RequestMapping("/enrollments")
@AllArgsConstructor
public class EnrollmentController {

    private final IEnrollmentService enrollmentService;

    @Operation(summary = "Enroll a user in a course", description = "Endpoint to enroll a user in a course")
    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResp.class)))
    @PostMapping
    public ResponseEntity<EnrollmentResp> enrollUser(@Validated @RequestBody EnrollmentReq request) {
        return ResponseEntity.ok(this.enrollmentService.create(request));
    }

    @Operation(summary = "Get enrollment information", description = "Endpoint to get detailed information of a specific enrollment")
    @ApiResponse(responseCode = "400", description = "Invalid enrollment ID", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResp.class)))
    @GetMapping("/{enrollment_id}")
    public ResponseEntity<EnrollmentResp> getEnrollmentInfo(@PathVariable("enrollment_id") Long enrollmentId) {
        return ResponseEntity.ok(this.enrollmentService.get(enrollmentId));
    }

    @Operation(summary = "Delete an enrollment", description = "Endpoint to delete an enrollment")
    @ApiResponse(responseCode = "400", description = "Invalid enrollment ID", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResp.class)))
    @DeleteMapping("/delete/{enrollment_id}")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable("enrollment_id") Long enrollmentId) {
        this.enrollmentService.delete(enrollmentId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get courses by user ID", description = "Endpoint to get all courses a specific user is enrolled in")
    @ApiResponse(responseCode = "400", description = "Invalid user ID", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResp.class)))
    @GetMapping("/user/{user_id}/courses")
    public ResponseEntity<List<EnrollmentResp>> getCoursesByUser(@PathVariable("user_id") Long userId) {
        return ResponseEntity.ok(this.enrollmentService.getCoursesByUserId(userId));
    }

    @Operation(summary = "Get users by course ID", description = "Endpoint to get all users enrolled in a specific course")
    @ApiResponse(responseCode = "400", description = "Invalid course ID", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResp.class)))
    @GetMapping("/course/{course_id}/users")
    public ResponseEntity<List<EnrollmentResp>> getUsersByCourse(@PathVariable("course_id") Long courseId) {
        return ResponseEntity.ok(this.enrollmentService.getUsersByCourseId(courseId));
    }
}

