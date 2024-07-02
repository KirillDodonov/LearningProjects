package com.dodonov.bootmvc.pets;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Pet {
    private Long id;
    private String name;
    private Long userId;
}
