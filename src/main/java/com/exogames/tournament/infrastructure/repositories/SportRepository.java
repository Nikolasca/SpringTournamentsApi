package com.exogames.tournament.infrastructure.repositories;

import com.exogames.tournament.domain.entities.Sport;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SportRepository extends MongoRepository<Sport, String> {

    Sport findByName(String name);
    List<Sport> findByActiveTrue();
}
