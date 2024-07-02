package com.dodonov.bootmvc.users;

import com.dodonov.bootmvc.pets.PetsDtoConverter;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConverter {

    private final PetsDtoConverter petDtoConverter;

    public UserDtoConverter(PetsDtoConverter petDtoConverter) {
        this.petDtoConverter = petDtoConverter;
    }

    public User toUser(UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getName(),
                userDto.getEmail(),
                userDto.getAge(),
                userDto.getPets().stream().map(petDtoConverter::toPet).toList()
        );
    }

    public UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getAge(),
                user.getPets().stream().map(petDtoConverter::toDto).toList()
        );
    }
}
