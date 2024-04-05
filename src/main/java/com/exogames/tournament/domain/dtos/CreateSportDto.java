package com.exogames.tournament.domain.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CreateSportDto {

    private String name;
    private byte[] icon;
}
