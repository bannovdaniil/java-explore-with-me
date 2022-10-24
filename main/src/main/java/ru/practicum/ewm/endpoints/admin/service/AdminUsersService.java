package ru.practicum.ewm.endpoints.admin.service;

import ru.practicum.ewm.exception.UserNotFoundException;
import ru.practicum.ewm.model.dto.users.UserDto;

import java.util.List;

public interface AdminUsersService {
    UserDto createUser(UserDto userDto);

    List<UserDto> findAll(Long[] ids, Integer from, Integer size);

    void deleteUserById(Long userId) throws UserNotFoundException;
}