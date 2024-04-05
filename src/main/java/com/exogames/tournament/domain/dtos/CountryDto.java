package com.exogames.tournament.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CountryDto {

    private String name;
    private int legalAge;
    private Date createdDate;
    private Date lasModifiedDate;
    private boolean active;

}
