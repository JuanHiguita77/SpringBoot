package com.juan.GraphQLCrud.api.dto;

import com.juan.GraphQLCrud.domain.entities.Brand;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class ModelDTO {

    private Long id;
    private String name;
    private Long brandId;
}
