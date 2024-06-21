package com.riwi.mapstruct.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

import com.riwi.mapstruct.entity.Category;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class GetProduct 
{
    private Long id;
    private String name;
    private LocalDateTime creationDate;
    
    private GetCategory category;
}
