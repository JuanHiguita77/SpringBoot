package riwi.lastfilter.spring.api.dto.response;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import riwi.lastfilter.spring.domain.entities.Buy;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse 
{
    @Schema(description = "User ID", example = "1212211545")
    private String id;

    @Schema(description = "User name", example = "tester")
    private String userName;

    @Schema(description = "Email", example = "test@gmail.com")
    private String email;
}
