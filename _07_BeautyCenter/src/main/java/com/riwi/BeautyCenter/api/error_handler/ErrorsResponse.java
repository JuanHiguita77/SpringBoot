package com.riwi.BeautyCenter.api.error_handler;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper=false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ErrorsResponse extends BaseErrorResponse
{
    private List<String> errors;
}
