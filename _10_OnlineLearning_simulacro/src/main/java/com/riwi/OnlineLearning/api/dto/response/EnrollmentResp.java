package com.riwi.OnlineLearning.api.dto.response;

import java.time.LocalDate;

import com.riwi.OnlineLearning.domain.entities.Course;
import com.riwi.OnlineLearning.domain.entities.User;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Response DTO for Enrollment entity")
public class EnrollmentResp {
    @Schema(description = "Enrollment ID", example = "1")
    private Long enrollmentId;

    @Schema(description = "User ID", example = "1")
    private User user;

    @Schema(description = "Course ID", example = "1")
    private Course course;

    @Schema(description = "Enrollment date")
    private LocalDate enrollmentDate;
}
