package riwi.lastfilter.spring.infrastructure.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import riwi.lastfilter.spring.api.dto.request.UserRequest;
import riwi.lastfilter.spring.api.dto.response.UserResponse;
import riwi.lastfilter.spring.domain.entities.User;
import riwi.lastfilter.spring.domain.repositories.UserRepository;
import riwi.lastfilter.spring.infrastructure.abstrasct_services.IUserService;
import riwi.lastfilter.spring.utils.mappers.UserMapper;

@Service
@AllArgsConstructor
public class UserService implements IUserService
{
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final UserMapper userMapper;

    @Override
    public UserResponse create(UserRequest request) 
    {
        User user = userMapper.toEntity(request);
        return userMapper.toDTOEntity(userRepository.save(user));
    }

    private Optional<User> find(String id) {
        return this.userRepository.findById(id);
            /* .orElseThrow(() -> new BadRequestException("No User found with the supplied ID"));*/
    }
        
}
