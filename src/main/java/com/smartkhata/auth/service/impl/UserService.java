package com.smartkhata.auth.service.impl;

import com.smartkhata.auth.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto create(UserDto dto);

    UserDto getById(Long id);

    List<UserDto> getByVendor(Long vendorId);

    UserDto update(Long id, UserDto dto);

    void delete(Long id);
}
