package com.exogames.tournament.domain.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UpdateCountryDto {

    private String id;
    private String name;
    private int legalAge;
}
