package ru.practicum.ewm.endpoints.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.Constants;
import ru.practicum.ewm.dto.users.UserDto;
import ru.practicum.ewm.endpoints.admin.service.AdminUsersService;
import ru.practicum.ewm.exception.UserNotFoundException;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
@Validated
@Slf4j
public class AdminUsersController {
    private final AdminUsersService adminUsersService;

    @PostMapping()
    public UserDto createUser(@Valid @RequestBody UserDto userDto) {
        log.info("createUser: {}", userDto);
        return adminUsersService.createUser(userDto);
    }

    @GetMapping
    public List<UserDto> findAll(
            @RequestParam(value = "ids") Long[] ids,
            @PositiveOrZero
            @RequestParam(defaultValue = "0") Integer from,
            @Positive
            @RequestParam(defaultValue = Constants.PAGE_SIZE_STRING) Integer size) {
        log.info("findAll: {},{}", ids, from);
        return adminUsersService.findAll(ids, from, size);
    }

    @DeleteMapping("{userId}")
    public void deleteUser(@Valid @Positive @PathVariable Long userId) throws UserNotFoundException {
        log.info("deleteUser: {}", userId);
        adminUsersService.deleteUserById(userId);
    }
}
