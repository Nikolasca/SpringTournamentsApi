package com.exogames.tournament.application.servicesImpl;

import com.exogames.tournament.domain.dtos.CreateSportDto;
import com.exogames.tournament.domain.dtos.SportDto;
import com.exogames.tournament.domain.dtos.UpdateSportDto;
import com.exogames.tournament.domain.entities.Sport;
import com.exogames.tournament.domain.services.SportService;
import com.exogames.tournament.infrastructure.exceptions.CountryNotFoundException;
import com.exogames.tournament.infrastructure.exceptions.ExistentCountryException;
import com.exogames.tournament.infrastructure.exceptions.ExistentSportException;
import com.exogames.tournament.infrastructure.exceptions.SportNotFoundException;
import com.exogames.tournament.infrastructure.repositories.SportRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SportServiceImpl implements SportService {

    private final SportRepository repository;

    public SportServiceImpl(SportRepository repository) {
        this.repository = repository;
    }

    public SportDto createSport (CreateSportDto createSportDto){
        validateId(createSportDto.getName());
        validateData(createSportDto.getName(), createSportDto.getIcon());

        Optional<Sport> searchedSport = Optional.ofNullable(repository.findByName(createSportDto.getName()));

        if (searchedSport.isPresent()){
            throw new ExistentSportException("A Sport with that name already exists.");
        }

        Sport newSport = new Sport();
        newSport.setName(createSportDto.getName());
        newSport.setIcon(createSportDto.getIcon());

        repository.save(newSport);

        Sport foundSport = repository.findByName(newSport.getName());

        return new SportDto(
                foundSport.getName(),
                foundSport.getIcon(),
                foundSport.getCreatedDate(),
                foundSport.getLastModifiedDate(),
                foundSport.isActive()
        );
    };

    public List<SportDto> getAllSports(){

        List<Sport> sportList = repository.findAll();
        List<SportDto> sportDtoList = new ArrayList<>();

        for (Sport sport: sportList){
            sportDtoList.add(new SportDto(
                    sport.getName(),
                    sport.getIcon(),
                    sport.getCreatedDate(),
                    sport.getLastModifiedDate(),
                    sport.isActive()
            ));
        }

        return sportDtoList;
    };

    public List<SportDto> getAllActiveSports(){
        List<Sport> sportList = repository.findByActiveTrue();
        List<SportDto> sportDtoList = new ArrayList<>();

        for (Sport sport: sportList){
            sportDtoList.add(new SportDto(
                    sport.getName(),
                    sport.getIcon(),
                    sport.getCreatedDate(),
                    sport.getLastModifiedDate(),
                    sport.isActive()
            ));
        }

        return sportDtoList;
    };

    public SportDto getSportById(String id){
        validateId(id);

        Optional<Sport> optionalSport = repository.findById(id);
        Sport foundSport = optionalSport.get();

        return new SportDto(
                foundSport.getName(),
                foundSport.getIcon(),
                foundSport.getCreatedDate(),
                foundSport.getLastModifiedDate(),
                foundSport.isActive()
        );
    };

    public SportDto updateSport(UpdateSportDto updateSportDto) {

        validateId(updateSportDto.getId());
        validateData(updateSportDto.getName(), updateSportDto.getIcon());

        Optional<Sport> optionalSport = repository.findById(updateSportDto.getId());
        Sport foundSport = optionalSport.get();

        foundSport.setName(updateSportDto.getName());
        foundSport.setIcon(updateSportDto.getIcon());

        repository.save(foundSport);

        Optional<Sport> optionalUpdatedSport = repository.findById(updateSportDto.getId());
        Sport updatedSport = optionalUpdatedSport.get();

        return new SportDto(
                updatedSport.getName(),
                updatedSport.getIcon(),
                updatedSport.getCreatedDate(),
                updatedSport.getLastModifiedDate(),
                updatedSport.isActive()
        );
    };

    public SportDto deactivateSport(String id){
        validateId(id);

        Optional<Sport> optionalSport = repository.findById(id);
        Sport foundSport = optionalSport.get();
        foundSport.setActive(false);

        repository.save(foundSport);

        Optional<Sport> optionalUpdatedSport = repository.findById(id);
        Sport updatedSport = optionalUpdatedSport.get();

        return new SportDto(
                updatedSport.getName(),
                updatedSport.getIcon(),
                updatedSport.getCreatedDate(),
                updatedSport.getLastModifiedDate(),
                updatedSport.isActive()
        );
    };

    public SportDto activateSport(String id){
        validateId(id);

        Optional<Sport> optionalSport = repository.findById(id);
        Sport foundSport = optionalSport.get();
        foundSport.setActive(true);

        repository.save(foundSport);

        Optional<Sport> optionalUpdatedSport = repository.findById(id);
        Sport updatedSport = optionalUpdatedSport.get();

        return new SportDto(
                updatedSport.getName(),
                updatedSport.getIcon(),
                updatedSport.getCreatedDate(),
                updatedSport.getLastModifiedDate(),
                updatedSport.isActive()
        );
    };

    public void deleteSport(String id){
        validateId(id);
        repository.deleteById(id);
    };

    private void validateId(String id){
        if (id == null || id.isEmpty()){
            throw new CountryNotFoundException("Sport not be found, invalid Id entered.");
        } else if (!repository.existsById(id)) {
            throw new CountryNotFoundException("Sport not be found, invalid Id entered.");
        }
    }

    private void validateData(String name, byte[] icon){
        if (name == null || name.isEmpty()){
            throw new SportNotFoundException("Invalid name entered.");
        }

        if (icon == null || icon.length == 0){
            throw new SportNotFoundException("Invalid icon entered.");
        }
    }
}
