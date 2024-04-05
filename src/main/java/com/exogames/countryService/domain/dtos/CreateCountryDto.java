package com.exogames.countryService.domain.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CreateCountryDto {

    private String name;
    private int legalAge;
}
