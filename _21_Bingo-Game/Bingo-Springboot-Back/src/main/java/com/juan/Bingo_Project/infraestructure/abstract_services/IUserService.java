package com.juan.Bingo_Project.infraestructure.abstract_services;

import com.juan.Bingo_Project.api.dto.UserDTO;
import com.juan.Bingo_Project.infraestructure.services.generic_services.CreateService;
import com.juan.Bingo_Project.infraestructure.services.generic_services.GetAllService;
import com.juan.Bingo_Project.infraestructure.services.generic_services.GetService;

public interface IUserService extends CreateService<UserDTO,UserDTO>/* , GetService<UserDTO,Long>, GetAllService<UserDTO> */{
    UserDTO findByUsername(String username);
}
