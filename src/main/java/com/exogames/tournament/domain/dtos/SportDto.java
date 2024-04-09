package com.exogames.tournament.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SportDto {

    private String id;
    private String name;
    private Binary icon;
    private Date createdDate;
    private Date lasModifiedDate;
    private boolean active;
}
