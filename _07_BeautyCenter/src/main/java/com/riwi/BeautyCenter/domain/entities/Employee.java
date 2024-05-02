package com.riwi.BeautyCenter.domain.entities;

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

@Entity(name = "employee")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 11)
    private Long employee_id;

    @Column(length = 100, nullable = false)
    private String first_name;

    @Column(length = 100, nullable = false)
    private String last_name;

    @Column(length = 100)
    private String email;

    @Column(length = 20, nullable = false)
    private String phone;

    @Column(length = 50, nullable = false)
    private String role;    

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Appointment> appointments;
}
