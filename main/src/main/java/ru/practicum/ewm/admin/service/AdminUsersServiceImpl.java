package ru.practicum.ewm.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.admin.mapper.UserMapper;
import ru.practicum.ewm.admin.model.User;
import ru.practicum.ewm.admin.repository.AdminUserRepository;
import ru.practicum.ewm.dto.users.UserDto;
import ru.practicum.ewm.exception.UserNotFoundException;

import java.security.InvalidParameterException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminUsersServiceImpl implements AdminUsersService {
    private final AdminUserRepository adminUserRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        if (userDto.getEmail() == null) {
            throw new InvalidParameterException("User Email is empty.");
        }
        User user = adminUserRepository.save(UserMapper.dtoToUser(userDto));
        return UserMapper.userToDto(user);
    }

    @Override
    public List<UserDto> findAll(Long[] ids, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size);
        return UserMapper.userListToDto(adminUserRepository.findAllByIds(ids, pageable));
    }

    @Override
    public void deleteUserById(Long userId) throws UserNotFoundException {
        if (!adminUserRepository.existsById(userId)) {
            throw new UserNotFoundException("User with was not found.");
        }
        adminUserRepository.deleteById(userId);
    }
}
