package com.riwi.OnlineLearning.api.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Response DTO for Submission entity")
public class SubmissionResp 
{
    @Schema(description = "Submission ID", example = "1")
    private Long submissionId;

    @Schema(description = "Content")
    private String content;

    @Schema(description = "Submission date")
    private LocalDate submissionDate;

    @Schema(description = "Grade", example = "85.00")
    private BigDecimal grade;

    @Schema(description = "User ID", example = "1")
    private Long userId;

    @Schema(description = "Assignment ID", example = "1")
    private Long assignmentId;
}
