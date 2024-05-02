package com.riwi.vacants.utils.dto.response;

import com.riwi.vacants.utils.enums.StateVacant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VacantToCompanyResponse
{
    private Long id;
    private String title;
    private String description;
    private StateVacant status;
    //Va a hacer lo mismo, pero sin responder con la compañia, para evitar bucles, es depende de como lo queramos mandar los datos al front
}
