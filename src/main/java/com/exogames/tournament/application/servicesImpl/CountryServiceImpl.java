package com.exogames.tournament.application.servicesImpl;

import com.exogames.tournament.domain.dtos.CountryDto;
import com.exogames.tournament.domain.dtos.CreateCountryDto;
import com.exogames.tournament.domain.entities.Country;
import com.exogames.tournament.domain.services.CountryService;
import com.exogames.tournament.infrastructure.exceptions.CountryNotFoundException;
import com.exogames.tournament.infrastructure.exceptions.ExistentCountryException;
import com.exogames.tournament.infrastructure.repositories.CountryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository repository;

    public CountryServiceImpl(CountryRepository countryRepository) {
        this.repository = countryRepository;
    }

    public CountryDto createCountry (CreateCountryDto createCountryDto){

        validateData(createCountryDto.getName(), createCountryDto.getLegalAge());

        Optional<Country> searchedCountry = Optional.ofNullable(repository.findByName(createCountryDto.getName()));

        if (searchedCountry.isPresent()) {
            throw new ExistentCountryException("A country with that name already exists.");
        }

        Country newCountry = new Country();
        newCountry.setName(createCountryDto.getName());
        newCountry.setLegalAge(createCountryDto.getLegalAge());

        repository.save(newCountry);

        Country foundCountry = repository.findByName(newCountry.getName());

        return new CountryDto(
                foundCountry.getId(),
                foundCountry.getName(),
                foundCountry.getLegalAge(),
                foundCountry.getCreatedDate(),
                foundCountry.getLastModifiedDate()
        );
    }
    public List<CountryDto> getAllCountries (){

        List<Country> countryList = repository.findAll();
        List<CountryDto> countries = new ArrayList<>();

        for (Country country:countryList){
            countries.add(new CountryDto(
                    country.getId(),
                    country.getName(),
                    country.getLegalAge(),
                    country.getCreatedDate(),
                    country.getLastModifiedDate()
            ));
        }

        return countries;
    }

    public List<CountryDto> getAllActiveCountries (){

        List<Country> countryList = repository.findByActiveTrue();
        List<CountryDto> countries = new ArrayList<>();

        for (Country country:countryList){
            countries.add(new CountryDto(
                    country.getId(),
                    country.getName(),
                    country.getLegalAge(),
                    country.getCreatedDate(),
                    country.getLastModifiedDate()
            ));
        }

        return countries;
    }

    public CountryDto getCountryById (String id) {

        validateId(id);

        Optional<Country> optionalCountry = repository.findById(id);
        Country foundCountry = optionalCountry.get();
        return new CountryDto(
                foundCountry.getId(),
                foundCountry.getName(),
                foundCountry.getLegalAge(),
                foundCountry.getCreatedDate(),
                foundCountry.getLastModifiedDate()
        );
    }

    public CountryDto updateCountry (CountryDto countryDto){

        validateId(countryDto.getId());
        validateData(countryDto.getName(), countryDto.getLegalAge());

        Optional<Country> optionalCountry = repository.findById(countryDto.getId());

        Country foundCountry = optionalCountry.get();

        foundCountry.setName(countryDto.getName());
        foundCountry.setLegalAge(countryDto.getLegalAge());

        repository.save(foundCountry);

        Optional<Country> optionalUpdatedCountry = repository.findById(countryDto.getId());
        Country updatedCountry = optionalUpdatedCountry.get();


        return new CountryDto(
                updatedCountry.getId(),
                updatedCountry.getName(),
                updatedCountry.getLegalAge(),
                updatedCountry.getCreatedDate(),
                updatedCountry.getLastModifiedDate()
        );
    }

    public void deactivateCountry (String id){
        validateId(id);

        Optional<Country> optionalCountry = repository.findById(id);
        Country foundCountry = optionalCountry.get();
        foundCountry.setActive(false);

        repository.save(foundCountry);
    }

    public void deleteCountry (String id){
        validateId(id);
        repository.deleteById(id);
    }

    private void validateId(String id){
        if (id == null || id.isEmpty()){
            throw new CountryNotFoundException("Country not be found, invalid Id entered.");
        } else if (!repository.existsById(id)) {
            throw new CountryNotFoundException("Country not be found, invalid Id entered.");
        }
    }

    private void validateData(String name, int legalAge){

        if (name == null || name.isEmpty()){
            throw new CountryNotFoundException("Invalid name entered.");
        }

        if (legalAge <= 0 || legalAge >= 30){
            throw new CountryNotFoundException("Invalid legal age entered.");
        }
    }

}