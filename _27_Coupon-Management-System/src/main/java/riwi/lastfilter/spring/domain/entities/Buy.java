package riwi.lastfilter.spring.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "buys")
public class Buy {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "coupon_Id", referencedColumnName = "id")
    private Coupon coupon;

    @ManyToOne
    @JoinColumn(name = "user_Id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_Id", referencedColumnName = "id")
    private Product product;
}
