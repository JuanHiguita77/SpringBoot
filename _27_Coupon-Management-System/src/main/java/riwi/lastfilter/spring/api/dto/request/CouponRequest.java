package riwi.lastfilter.spring.api.dto.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import riwi.lastfilter.spring.utils.enums.StateType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CouponRequest {

    @NotBlank(message = "The expiration date is required")
    private LocalDateTime expirationDate;

    @NotBlank(message = "The discount percent is required")
    private double discountPercent;

    @NotBlank(message = "The discount percent is required")
    private StateType state;

}
