package com.riwi.BeautyCenter.api.dto.request;

import java.math.BigDecimal;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceRequest 
{
    @Size(min = 3, max = 100)
    @NotBlank(message = "Service Name is required")
    private String name;

    @Size(min = 10, max = 60)
    private String description;

    //@DecimalMin(value = "0.00", inclusive = false, message = "Price must be greater than 0")
    @DecimalMax(value = "99999999.99", inclusive = true, message = "Price must be less than or equal to 99999999.99")
    @NotNull(message = "Price is required")
    private BigDecimal price;
}
