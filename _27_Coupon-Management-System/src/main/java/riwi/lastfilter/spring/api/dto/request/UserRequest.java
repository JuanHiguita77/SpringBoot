package riwi.lastfilter.spring.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest 
{
    @NotBlank(message = "Identification is required")
    @Size(max = 10)
    private String id;

    @NotBlank(message = "User Name is required")
    @Size(max = 45)
    private String userName;

    @Size(max = 100)
    @NotBlank(message = "Email is required")
    private String email;

    @Size(max = 64)
    @NotBlank(message = "Password is required")
    private String password;
}
