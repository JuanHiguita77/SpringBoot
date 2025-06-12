package com.juan.patient_managment.util.exceptions;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse implements Serializable
{ 
    private List<Map<String, String>> errors;
    private String status; 
    private Integer code; 
}
