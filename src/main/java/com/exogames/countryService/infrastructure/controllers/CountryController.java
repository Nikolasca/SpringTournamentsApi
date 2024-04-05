package com.exogames.countryService.infrastructure.controllers;

import com.exogames.countryService.application.servicesImpl.CountryServiceImpl;
import com.exogames.countryService.domain.dtos.CountryDto;
import com.exogames.countryService.domain.dtos.CreateCountryDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/countries")
public class CountryController {

    private final CountryServiceImpl service;

    public CountryController(CountryServiceImpl service) {
        this.service = service;
    }

    @GetMapping("")
    public ResponseEntity<List<CountryDto>> getAllCountries(){
        List<CountryDto> countries = service.getAllCountries();
        return new ResponseEntity<>(countries, HttpStatus.OK);
    }

    @GetMapping("/active")
    public ResponseEntity<List<CountryDto>> getAllActiveCountries(){
        List<CountryDto> activeCountries = service.getAllActiveCountries();
        return new ResponseEntity<>(activeCountries, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CountryDto> getCountry(@PathVariable String id){
        CountryDto country = service.getCountryById(id);
        return new ResponseEntity<>(country, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<CountryDto> createCountry(@RequestBody CreateCountryDto createCountryDto){
        CountryDto createdCountry = service.createCountry(createCountryDto);
        return new ResponseEntity<>(createdCountry, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<CountryDto> updateCountry(@RequestBody CountryDto countryDto){
        CountryDto udpatedCountry = service.updateCountry(countryDto);
        return new ResponseEntity<>(udpatedCountry, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> deactivateCountry(@PathVariable String id){
        service.deactivateCountry(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCountry(@PathVariable String id){
        service.deleteCountry(id);
        return ResponseEntity.ok().build();
    }
}
