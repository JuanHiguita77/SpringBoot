package riwi.lastfilter.spring.api.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import riwi.lastfilter.spring.domain.entities.Buy;
import riwi.lastfilter.spring.utils.enums.StateType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CouponResponse {

    private Long id;
    private LocalDateTime expirationDate;
    private double discountPercent;
    private StateType state;
    private List<Buy> buys;

}
