package com.exogames.tournament.domain.services;

import com.exogames.tournament.domain.dtos.CountryDto;
import com.exogames.tournament.domain.dtos.CreateCountryDto;
import com.exogames.tournament.domain.dtos.UpdateCountryDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CountryService {

    CountryDto createCountry (CreateCountryDto createCountryDto);
    List<CountryDto> getAllCountries();
    List<CountryDto> getAllActiveCountries();
    CountryDto getCountryById(String id);
    CountryDto updateCountry(UpdateCountryDto updateCountryDto);
    CountryDto deactivateCountry(String id);
    CountryDto activateCountry(String id);
    void deleteCountry(String id);
}
