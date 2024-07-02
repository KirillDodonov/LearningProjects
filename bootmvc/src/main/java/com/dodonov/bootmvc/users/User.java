package com.dodonov.bootmvc.users;

import com.dodonov.bootmvc.pets.Pet;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class User {
    private Long id;
    private String name;
    private String email;
    private Integer age;
    private List<Pet> pets;
}
