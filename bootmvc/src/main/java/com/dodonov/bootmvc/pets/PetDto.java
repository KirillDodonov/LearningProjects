package com.dodonov.bootmvc.pets;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PetDto {
    private Long id;
    @NotBlank(message = "Name must be not empty")
    private String name;
    @Nonnull
    private Long userId;
}
