package com.juan.patient_managment.util.exceptions;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lombok.Builder;

@Builder
public record ErrorResponse(
    List<Map<String, String>> errors, 
    String status, 
    Integer code) implements Serializable
{}
