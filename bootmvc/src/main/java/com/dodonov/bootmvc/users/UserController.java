package com.dodonov.bootmvc.users;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserDtoConverter userDtoConverter;

    public UserController(UserService userService, UserDtoConverter userDtoConverter) {
        this.userService = userService;
        this.userDtoConverter = userDtoConverter;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable("userId") Long userId) {
        User user = userService.getUser(userId);
        return ResponseEntity.ok(userDtoConverter.toDto(user));
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody @Validated UserDto userDto) {
        User user = userService.createUser(userDtoConverter.toUser(userDto));
        return ResponseEntity.ok(userDtoConverter.toDto(user));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable("userId") Long userId,
            @RequestBody @Validated UserDto userDto
    ) {
        User userToUpdate = userDtoConverter.toUser(userDto);
        userToUpdate.setId(userId);
        User updatedUser = userService.updateUser(userToUpdate);
        return ResponseEntity.ok(userDtoConverter.toDto(updatedUser));
    }
}
