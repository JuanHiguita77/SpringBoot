package com.riwi.OnlineLearning.api.dto.response;

import java.time.LocalDate;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Response DTO for Assignment entity")
public class AssignmentResp 
{
    @Schema(description = "Assignment ID", example = "1")
    private Long assignmentId;

    @Schema(description = "Assignment title", example = "Homework 1")
    private String assignmentTitle;

    @Schema(description = "Description")
    private String description;

    @Schema(description = "Due date")
    private LocalDate dueDate;

    @Schema(description = "Lesson ID", example = "1")
    private Long lessonId;    

    @Schema(description = "List of submissions for the assignment")
    private List<SubmissionResp> submissions;
}
