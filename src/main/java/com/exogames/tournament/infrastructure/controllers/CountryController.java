package com.exogames.tournament.infrastructure.controllers;

import com.exogames.tournament.application.servicesImpl.CountryServiceImpl;
import com.exogames.tournament.domain.dtos.CountryDto;
import com.exogames.tournament.domain.dtos.CreateCountryDto;
import com.exogames.tournament.domain.dtos.UpdateCountryDto;
import com.exogames.tournament.infrastructure.exceptions.CountryNotFoundException;
import com.exogames.tournament.infrastructure.exceptions.ExistentCountryException;
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

    // GET ACTIVE COUNTRIES
    /*    @GetMapping("/active")
    public ResponseEntity<List<CountryDto>> getAllActiveCountries(){
        List<CountryDto> activeCountries = service.getAllActiveCountries();
        return new ResponseEntity<>(activeCountries, HttpStatus.OK);
    }*/

    @GetMapping("") // GET ALL COUNTRIES
    public ResponseEntity<List<CountryDto>> getAllCountries(){
        List<CountryDto> countries = service.getAllCountries();
        return new ResponseEntity<>(countries, HttpStatus.OK);
    }

    @GetMapping("/{id}") // GET COUNTRY
    public ResponseEntity<?> getCountry(@PathVariable String id){
        try{
            CountryDto country = service.getCountryById(id);
            return new ResponseEntity<>(country, HttpStatus.OK);
        }catch (CountryNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PostMapping("") // CREATE NEW COUNTRY
    public ResponseEntity<?> createCountry(@RequestBody CreateCountryDto createCountryDto){
        try {
            CountryDto createdCountry = service.createCountry(createCountryDto);
            return new ResponseEntity<>(createdCountry, HttpStatus.CREATED);
        }catch (ExistentCountryException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }catch (CountryNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }

    }

    @PutMapping("") // UPDATE COUNTRY
    public ResponseEntity<?> updateCountry(@RequestBody UpdateCountryDto updateCountryDto){
        try {
            CountryDto udpatedCountry = service.updateCountry(updateCountryDto);
            return new ResponseEntity<>(udpatedCountry, HttpStatus.OK);
        }catch (CountryNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/deactivate") // DEACTIVATE COUNTRY
    public ResponseEntity<?> deactivateCountry(@RequestParam String id){
        try{
            CountryDto udpatedCountry = service.deactivateCountry(id);
            return new ResponseEntity<>(udpatedCountry, HttpStatus.OK);
        }catch (CountryNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/activate") // ACTIVATE COUNTRY
    public ResponseEntity<?> activateCountry(@RequestParam String id){
        try{
            CountryDto udpatedCountry = service.activateCountry(id);
            return new ResponseEntity<>(udpatedCountry, HttpStatus.OK);
        }catch (CountryNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}") // DELETE COUNTRY
    public ResponseEntity<?> deleteCountry(@PathVariable String id){
        try{
            service.deleteCountry(id);
            return ResponseEntity.ok().build();
        }catch (CountryNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
