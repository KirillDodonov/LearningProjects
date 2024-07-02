package com.dodonov.bootmvc.pets;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pets")
public class PetsController {

    private final PetsDtoConverter petsDtoConverter;
    private final PetsService petsService;

    public PetsController(PetsDtoConverter petsDtoConverter, PetsService petsService) {
        this.petsDtoConverter = petsDtoConverter;
        this.petsService = petsService;
    }

    @GetMapping("/{petId}")
    public ResponseEntity<PetDto> getPet(@PathVariable("petId") Long petId) {
        Pet pet = petsService.getPet(petId);
        return ResponseEntity.ok(petsDtoConverter.toDto(pet));
    }

    @PostMapping
    public ResponseEntity<PetDto> createPet(@RequestBody @Validated PetDto petDto) {
        Pet pet = petsService.createPet(petsDtoConverter.toPet(petDto));
        return ResponseEntity.ok(petsDtoConverter.toDto(pet));
    }

    @DeleteMapping("/{petId}")
    public ResponseEntity<Void> deletePet(@PathVariable("petId") Long petId) {
        petsService.deletePet(petId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{petId}")
    public ResponseEntity<PetDto> updatePet(
            @PathVariable("petId") Long petId,
            @RequestBody @Validated PetDto petDto
    ) {
        Pet petToUpdate = petsDtoConverter.toPet(petDto);
        petToUpdate.setId(petId);
        Pet updatedPet = petsService.updatePet(petToUpdate);
        return ResponseEntity.ok(petsDtoConverter.toDto(updatedPet));
    }
}
