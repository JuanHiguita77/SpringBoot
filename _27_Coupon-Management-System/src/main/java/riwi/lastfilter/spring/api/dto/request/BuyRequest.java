package riwi.lastfilter.spring.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuyRequest {

    @Size(min = 1)
    @NotNull(message = "Coupon id is required")
    private Long couponId;

    @NotBlank(message = "User id is required")
    private String userId;

    @Size(min = 1)
    @NotNull(message = "Product id is required")
    private Integer productId;
}