package com.dodonov.bootmvc.pets;

import com.dodonov.bootmvc.users.User;
import com.dodonov.bootmvc.users.UserService;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PetsService {

    private final AtomicLong idCounter;
    private final UserService userService;

    public PetsService(UserService userService) {
        this.idCounter = new AtomicLong();
        this.userService = userService;
    }

    public Pet getPet(Long id) {
        return getPetById(id)
                .orElseThrow(() -> new NoSuchElementException("No such pet with id=%s".formatted(id)));
    }

    public Pet createPet(Pet pet) {
        if (pet.getId() != null) {
            throw new IllegalArgumentException("Id for user should not be provided");
        }
        Long id = idCounter.incrementAndGet();
        pet.setId(id);
        userService.getUser(pet.getUserId())
                .getPets()
                .add(pet);
        return pet;
    }

    public void deletePet(Long id) {
        Pet pet = getPetById(id)
                .orElseThrow(() -> new NoSuchElementException("No such pet with id=%s".formatted(id)));
        userService.getUser(pet.getUserId())
                .getPets().remove(pet);
    }

    public Pet updatePet(Pet pet) {
        if (pet.getId() == null) {
            throw new IllegalArgumentException("No user id passed");
        }
        Pet foundPet = getPetById(pet.getId())
                .orElseThrow(() -> new NoSuchElementException("No pet with id=%s".formatted(pet.getId())));
        foundPet.setName(pet.getName());
        return foundPet;
    }

    public Optional<Pet> getPetById(Long id) {
        return userService.getAllUsers().stream()
                .flatMap(user -> user.getPets().stream())
                .filter(pet -> pet.getId().equals(id))
                .findAny();
    }
}
