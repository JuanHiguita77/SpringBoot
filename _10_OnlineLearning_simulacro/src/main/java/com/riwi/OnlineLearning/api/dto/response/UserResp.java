package com.riwi.OnlineLearning.api.dto.response;

import java.util.List;

import com.riwi.OnlineLearning.utils.enums.Role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Response DTO for User entity")
public class UserResp 
{
    @Schema(description = "User ID", example = "1")
    private Long userId;

    @Schema(description = "Username", example = "john_doe")
    private String userName;

    @Schema(description = "Email", example = "john.doe@example.com")
    private String email;

    @Schema(description = "Full name", example = "John Doe")
    private String fullName;

    @Schema(description = "User role", example = "STUDENT")
    private Role role;

    @Schema(description = "List of enrollments of the user")
    private List<EnrollmentResp> enrollments;

    @Schema(description = "List of submissions of the user")
    private List<SubmissionResp> submissions;

    @Schema(description = "List of sent messages")
    private List<MessageResp> sentMessages;

    @Schema(description = "List of received messages")
    private List<MessageResp> receivedMessages;

    @Schema(description = "List of courses taught by the user")
    private List<CourseResp> courses;

}
