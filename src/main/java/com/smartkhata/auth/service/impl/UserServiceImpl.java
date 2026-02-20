package com.smartkhata.auth.service.impl;

import com.smartkhata.auth.dto.UserDto;
import com.smartkhata.auth.entity.User;
import com.smartkhata.auth.repository.UserRepository;
import com.smartkhata.common.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Override
    public UserDto create(UserDto dto) {
        User user = mapper.map(dto, User.class);
        return mapper.map(userRepository.save(user), UserDto.class);
    }

    @Override
    public UserDto getById(Long id) {
        return mapper.map(
                userRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found")),
                UserDto.class
        );
    }

    @Override
    public List<UserDto> getByVendor(Long vendorId) {
        return userRepository.findByVendorId(vendorId)
                .stream()
                .map(u -> mapper.map(u, UserDto.class))
                .toList();
    }

    @Override
    public UserDto update(Long id, UserDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        mapper.map(dto, user);
        return mapper.map(userRepository.save(user), UserDto.class);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
