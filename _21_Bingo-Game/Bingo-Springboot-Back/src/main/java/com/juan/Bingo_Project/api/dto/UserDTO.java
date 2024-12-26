package com.juan.Bingo_Project.api.dto;

import com.juan.Bingo_Project.util.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserDTO {

    private String userName;
    private String password;
    private Role role;
}
