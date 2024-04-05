package com.exogames.tournament.domain.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CreateCountryDto {

    private String name;
    private int legalAge;
}
