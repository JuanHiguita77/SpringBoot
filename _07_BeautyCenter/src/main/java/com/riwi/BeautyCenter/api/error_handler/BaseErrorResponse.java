package com.riwi.BeautyCenter.api.error_handler;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
//Se usa Serializable y SuperBuilder para usar el constructor builder de clases
public class BaseErrorResponse implements Serializable{
    private String status;
    private Integer code;
}
