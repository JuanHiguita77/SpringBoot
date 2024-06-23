package com.riwi.librosYa.infraestructure.services;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.riwi.librosYa.api.dto.DTOUser;
import com.riwi.librosYa.api.mappers.UserMapper;
import com.riwi.librosYa.domain.entities.User;
import com.riwi.librosYa.domain.repository.UserRepository;
import com.riwi.librosYa.infraestructure.abstract_Services.IUserService;
import com.riwi.librosYa.util.enums.SortType;
import com.riwi.librosYa.util.exceptions.BadRequestException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements IUserService
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public DTOUser create(DTOUser request) {
        User userEntity = userMapper.toEntity(request);
        return userMapper.toDTOUser(userRepository.save(userEntity));
    }

    @Override
    public DTOUser get(Long id) {
        User userEntity = this.find(id);
        return userMapper.toDTOUser(userEntity);
    }

    @Override
    public DTOUser update(DTOUser request, Long id) {
        User existingEntity = this.find(id);

        User updatedEntity = userMapper.toEntity(request);
        updatedEntity.setId(existingEntity.getId());

        return userMapper.toDTOUser(userRepository.save(updatedEntity));

    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    private User find(Long id) {
    return this.userRepository.findById(id)
            .orElseThrow(() -> new BadRequestException("No User found with the supplied ID"));
    }

    @Override
    public Page<DTOUser> getAll(int page, int size, SortType sort) {
        // TODO Auto-generated method stub
        return null;
    }
}