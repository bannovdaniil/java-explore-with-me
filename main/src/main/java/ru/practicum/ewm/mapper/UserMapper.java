package ru.practicum.ewm.mapper;

import ru.practicum.ewm.dto.users.UserDto;
import ru.practicum.ewm.dto.users.UserPublicDto;
import ru.practicum.ewm.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {
    public static UserDto userToDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRate());
    }

    public static User dtoToUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        return user;
    }

    public static List<UserDto> userListToDto(List<User> userList) {
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : userList) {
            userDtoList.add(userToDto(user));
        }
        return userDtoList;
    }

    public static UserPublicDto userToPublicDto(User user) {
        return new UserPublicDto(user.getName());
    }
}
