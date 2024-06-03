package com.riwi.OnlineLearning.api.controllers;

import com.riwi.OnlineLearning.api.dto.errors.ErrorsResp;
import com.riwi.OnlineLearning.api.dto.request.LessonReq;
import com.riwi.OnlineLearning.api.dto.response.LessonResp;
import com.riwi.OnlineLearning.infraestructure.abstract_services.ILessonService;

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

@Tag(name = "Lessons")
@RestController
@RequestMapping("/lessons")
@AllArgsConstructor
public class LessonController {

    private final ILessonService lessonService;

    @Operation(summary = "Create a new lesson", description = "Endpoint to create a new lesson")
    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResp.class)))
    @PostMapping
    public ResponseEntity<LessonResp> createLesson(@Validated @RequestBody LessonReq request) {
        return ResponseEntity.ok(this.lessonService.create(request));
    }

    @Operation(summary = "Get lesson information", description = "Endpoint to get detailed information of a specific lesson")
    @ApiResponse(responseCode = "400", description = "Invalid lesson ID", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResp.class)))
    @GetMapping("/{lesson_id}")
    public ResponseEntity<LessonResp> getLessonInfo(@PathVariable("lesson_id") Long lessonId) {
        return ResponseEntity.ok(this.lessonService.get(lessonId));
    }

    @Operation(summary = "Update lesson information", description = "Endpoint to update lesson information")
    @ApiResponse(responseCode = "400", description = "Invalid lesson ID", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResp.class)))
    @PutMapping("/update/{lesson_id}")
    public ResponseEntity<LessonResp> updateLesson(@PathVariable("lesson_id") Long lessonId,
                                                   @Validated @RequestBody LessonReq request) {
        return ResponseEntity.ok(this.lessonService.update(request, lessonId));
    }

    @Operation(summary = "Delete a lesson", description = "Endpoint to delete a lesson")
    @ApiResponse(responseCode = "400", description = "Invalid lesson ID", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResp.class)))
    @DeleteMapping("/delete/{lesson_id}")
    public ResponseEntity<Void> deleteLesson(@PathVariable("lesson_id") Long lessonId) {
        this.lessonService.delete(lessonId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get lessons by course ID", description = "Endpoint to get all lessons of a specific course")
    @ApiResponse(responseCode = "400", description = "Invalid course ID", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResp.class)))
    @GetMapping("/course/{course_id}/lessons")
    public ResponseEntity<List<LessonResp>> getLessonsByCourse(@PathVariable("course_id") Long courseId) {
        return ResponseEntity.ok(this.lessonService.getLessonsByCourseId(courseId));
    }
}

