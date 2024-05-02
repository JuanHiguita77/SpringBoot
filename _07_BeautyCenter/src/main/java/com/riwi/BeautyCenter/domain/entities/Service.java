package com.riwi.BeautyCenter.domain.entities;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "service")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Service 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long service_id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 60)
    private String description;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Appointment> appointments;
}
