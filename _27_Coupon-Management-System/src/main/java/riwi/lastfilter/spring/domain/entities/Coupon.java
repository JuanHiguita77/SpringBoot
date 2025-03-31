package riwi.lastfilter.spring.domain.entities;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import riwi.lastfilter.spring.utils.enums.StateType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "coupons")
public class Coupon {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime expirationDate;

    private double discountPercent;

    private StateType state;

    @OneToMany(
        mappedBy = "coupon",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<Buy> buys;
}
