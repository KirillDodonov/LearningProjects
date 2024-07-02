package com.dodonov.bootmvc.users;

import com.dodonov.bootmvc.pets.PetDto;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserDto {
    private Long id;
    @NotBlank(message = "Name must be not empty")
    private String name;
    @Email(message = "Email must be valid")
    private String email;
    @Min(value = 0, message = "Age must be more than 0")
    private Integer age;
    @Nonnull
    private List<PetDto> pets;
}
