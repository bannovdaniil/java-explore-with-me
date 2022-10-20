package ru.practicum.ewm.endpoints.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.users.UserDto;
import ru.practicum.ewm.endpoints.admin.repository.AdminUsersRepository;
import ru.practicum.ewm.exception.UserNotFoundException;
import ru.practicum.ewm.mapper.UserMapper;
import ru.practicum.ewm.model.User;

import java.security.InvalidParameterException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminUsersServiceImpl implements AdminUsersService {
    private final AdminUsersRepository adminUsersRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        if (userDto.getEmail() == null) {
            throw new InvalidParameterException("User Email is empty.");
        }
        User user = adminUsersRepository.save(UserMapper.dtoToUser(userDto));
        return UserMapper.userToDto(user);
    }

    @Override
    public List<UserDto> findAll(Long[] ids, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size);
        return UserMapper.userListToDto(adminUsersRepository.findAllByIds(ids, pageable));
    }

    @Override
    public void deleteUserById(Long userId) throws UserNotFoundException {
        if (!adminUsersRepository.existsById(userId)) {
            throw new UserNotFoundException("User with was not found.");
        }
        adminUsersRepository.deleteById(userId);
    }
}
