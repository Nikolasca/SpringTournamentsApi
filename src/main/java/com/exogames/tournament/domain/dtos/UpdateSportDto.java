package com.exogames.tournament.domain.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;

import java.util.Date;
@NoArgsConstructor
@Data
public class UpdateSportDto {

    private String id;
    private String name;
}
