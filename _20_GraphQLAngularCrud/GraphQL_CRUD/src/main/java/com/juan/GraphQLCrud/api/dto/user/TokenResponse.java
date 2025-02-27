package com.juan.GraphQLCrud.api.dto.user;

import com.juan.GraphQLCrud.util.enums.TokenType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponse {

    private String token;
    private String refreshToken; 

}
