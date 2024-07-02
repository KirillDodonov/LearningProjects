package com.dodonov.bootmvc.pets;

import org.springframework.stereotype.Component;

@Component
public class PetsDtoConverter {

    public Pet toPet(PetDto petDto) {
        return new Pet(
                petDto.getId(),
                petDto.getName(),
                petDto.getUserId()
        );
    }

    public PetDto toDto(Pet pet) {
        return new PetDto(
                pet.getId(),
                pet.getName(),
                pet.getUserId()
        );
    }
}
