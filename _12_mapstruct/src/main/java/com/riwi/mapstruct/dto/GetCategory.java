package com.riwi.mapstruct.dto;

import com.riwi.mapstruct.entity.Category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetCategory 
{
    private long id;    
    private String name;
    private boolean status;

}
