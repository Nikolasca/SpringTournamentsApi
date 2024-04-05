package com.exogames.tournament.infrastructure.repositories;

import com.exogames.tournament.domain.entities.Country;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends MongoRepository<Country, String> {
    Country findByName(String name);
    List<Country> findByActiveTrue();

}
