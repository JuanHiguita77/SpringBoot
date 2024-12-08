package com.juan.GraphQLCrud.api.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.juan.GraphQLCrud.domain.entities.Model;
import com.juan.GraphQLCrud.util.enums.Country;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class BrandDTO {

    private Long id;
    private String name;
    private Country country;

    private List<ModelDTO> models;
}
