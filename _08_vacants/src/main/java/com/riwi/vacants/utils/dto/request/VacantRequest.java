package com.riwi.vacants.utils.dto.request;

import com.riwi.vacants.utils.enums.StateVacant;

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
    private String title;
    private String description;
    private StateVacant status;
    //No pedimos una compañia completa, pedimos el id para identificar la que ya esta creada
    private String companyId;
}
