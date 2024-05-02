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

@Entity(name = "client")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 11)
    private Long client_id;

    @Column(length = 100, nullable = false)
    private String first_name;

    @Column(length = 100, nullable = false)
    private String last_name;

    @Column(length = 20)
    private String phone;

    @Column(length = 100)
    private String email; 

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Appointment> appointments;
}
