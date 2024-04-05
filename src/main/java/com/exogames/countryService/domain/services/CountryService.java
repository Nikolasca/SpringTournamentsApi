package com.exogames.countryService.domain.services;

import com.exogames.countryService.domain.dtos.CountryDto;
import com.exogames.countryService.domain.dtos.CreateCountryDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CountryService {

    CountryDto createCountry (CreateCountryDto createCountryDto);
    List<CountryDto> getAllCountries();
    List<CountryDto> getAllActiveCountries();
    CountryDto getCountryById(String id);
    CountryDto updateCountry(CountryDto countryDto);
    void deactivateCountry(String id);
    void deleteCountry(String id);
}
