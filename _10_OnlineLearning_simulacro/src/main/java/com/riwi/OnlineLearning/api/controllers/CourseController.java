package com.riwi.OnlineLearning.api.controllers;

import com.riwi.OnlineLearning.api.dto.errors.ErrorsResp;
import com.riwi.OnlineLearning.api.dto.request.CourseReq;
import com.riwi.OnlineLearning.api.dto.response.CourseResp;
import com.riwi.OnlineLearning.infraestructure.abstract_services.ICourseService;
import com.riwi.OnlineLearning.utils.enums.SortType;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Courses")
@RestController
@RequestMapping("/courses")
@AllArgsConstructor
public class CourseController {

    private final ICourseService courseService;

    @Operation(summary = "Create a new course", description = "Endpoint to create a new course")
    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResp.class)))
    @PostMapping
    public ResponseEntity<CourseResp> createCourse(@Validated @RequestBody CourseReq request) {
        return ResponseEntity.ok(this.courseService.create(request));
    }

    @Operation(summary = "Get course information", description = "Endpoint to get detailed information of a specific course")
    @ApiResponse(responseCode = "400", description = "Invalid course ID", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResp.class)))
    @GetMapping("/{course_id}")
    public ResponseEntity<CourseResp> getCourseInfo(@PathVariable("course_id") Long courseId) {
        return ResponseEntity.ok(this.courseService.get(courseId));
    }

    @Operation(summary = "Update course information", description = "Endpoint to update course information")
    @ApiResponse(responseCode = "400", description = "Invalid course ID", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResp.class)))
    @PutMapping("/update/{course_id}")
    public ResponseEntity<CourseResp> updateCourse(@PathVariable("course_id") Long courseId,
                                                   @Validated @RequestBody CourseReq request) {
        return ResponseEntity.ok(this.courseService.update(request, courseId));
    }

    @Operation(summary = "Delete a course", description = "Endpoint to delete a course")
    @ApiResponse(responseCode = "400", description = "Invalid course ID", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResp.class)))
    @DeleteMapping("/delete/{course_id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable("course_id") Long courseId) {
        this.courseService.delete(courseId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get all courses", description = "Endpoint to get a list of all courses")
    @ApiResponse(responseCode = "400", description = "Invalid pagination parameters", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResp.class)))
    @GetMapping
    public ResponseEntity<Page<CourseResp>> getAllCourses(@RequestParam(defaultValue = "1") int page,
                                                          @RequestParam(defaultValue = "10") int size,
                                                          @RequestHeader(required = false) SortType sortType) {
        if (sortType == null) sortType = SortType.NONE;
        return ResponseEntity.ok(this.courseService.getAll(page - 1, size, sortType));
    }
}
