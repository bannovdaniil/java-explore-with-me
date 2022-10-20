package ru.practicum.ewm.admin.service;

import ru.practicum.ewm.dto.users.UserDto;
import ru.practicum.ewm.exception.UserNotFoundException;

import java.util.List;

public interface AdminUsersService {
    UserDto createUser(UserDto userDto);

    List<UserDto> findAll(Long[] ids, Integer from, Integer size);

    void deleteUserById(Long userId) throws UserNotFoundException;

}
