package com.riwi.vacants.entities;

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

@Entity(name = "company")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(length = 40, nullable = false)
    private String name;
    private String location;
    @Column(length = 14, nullable = false)

    private String contact; 

    //Se le pasa el nombre del atributo en la otra entidad
    //Tipo Eager es el default que trae todo lo asignado con esta relacion
    //OrphanRemoval: Puede remover o dejar nullos los datos cuando se quedan sin llave foranea, el equivalente a delete cascade
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = false)
    @ToString.Exclude//Excluye el toString para evitar un ciclo infinito
    @EqualsAndHashCode.Exclude
    private List<Vacant> vacants;//La relacion va a devolver una lista ya que es de uno a muchos
}
