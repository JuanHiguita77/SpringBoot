package com.riwi.vacants.utils.dto.request;

import com.riwi.vacants.utils.enums.StateVacant;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VacantRequest 
{
    //Esta informacion se pasa por el postman en el objeto json

    @NotBlank(message = "Title required")
    private String title;

    @NotBlank(message = "Description required")
    private String description;
    private StateVacant status;

    //No pedimos una compañia completa, pedimos el id para identificar la que ya esta creada
    @Size(max = 36, min = 0)
    @NotBlank(message = "Company ID required")
    private String companyId;
}
