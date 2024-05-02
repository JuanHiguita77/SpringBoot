package com.riwi.vacants.entities;

import com.riwi.vacants.utils.enums.StateVacant;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "vacant")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vacant 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private StateVacant status;

    
    @ManyToOne(fetch = FetchType.LAZY)
    //Relacion de lo que devuelve la llave foranea
    //Llave foranea con el apodo y el nombre tal cual en la entidad
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;
}
