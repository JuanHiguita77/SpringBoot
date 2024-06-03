package com.riwi.OnlineLearning.api.dto.response;

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
@Schema(description = "Response DTO for Lesson entity")
public class LessonResp 
{
    @Schema(description = "Lesson ID", example = "1")
    private Long lessonId;

    @Schema(description = "Lesson title", example = "Introduction to Algebra")
    private String lessonTitle;

    @Schema(description = "Content")
    private String content;

    @Schema(description = "Course ID", example = "1")
    private Long courseId;    

    @Schema(description = "List of assignments for the lesson")
    private List<AssignmentResp> assignments;
}
