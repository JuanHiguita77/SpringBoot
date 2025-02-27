package com.juan.GraphQLCrud.api.dto;

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
