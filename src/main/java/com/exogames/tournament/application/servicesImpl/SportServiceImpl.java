package com.exogames.tournament.application.servicesImpl;

import com.exogames.tournament.domain.dtos.CreateSportDto;
import com.exogames.tournament.domain.dtos.SportDto;
import com.exogames.tournament.domain.dtos.UpdateSportDto;
import com.exogames.tournament.domain.entities.Sport;
import com.exogames.tournament.domain.services.SportService;
import com.exogames.tournament.infrastructure.exceptions.*;
import com.exogames.tournament.infrastructure.repositories.SportRepository;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SportServiceImpl implements SportService {

    private final SportRepository repository;

    public SportServiceImpl(SportRepository repository) {
        this.repository = repository;
    }

    public SportDto createSport (CreateSportDto createSportDto, MultipartFile icon) throws IOException {

        validateName(createSportDto.getName());
        validateImage(icon);

        Binary imageBinary = new Binary(icon.getBytes());

        Optional<Sport> searchedSport = Optional.ofNullable(repository.findByName(createSportDto.getName()));

        if (searchedSport.isPresent()){
            throw new ExistentSportException("A Sport with that name already exists.");
        }

        Sport newSport = new Sport();
        newSport.setName(createSportDto.getName());
        newSport.setIcon(imageBinary);

        repository.save(newSport);

        Sport foundSport = repository.findByName(newSport.getName());

        return new SportDto(
                foundSport.getId(),
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
                    sport.getId(),
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
                    sport.getId(),
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
                foundSport.getId(),
                foundSport.getName(),
                foundSport.getIcon(),
                foundSport.getCreatedDate(),
                foundSport.getLastModifiedDate(),
                foundSport.isActive()
        );
    };

    public SportDto updateSport(UpdateSportDto updateSportDto, MultipartFile icon) throws IOException {

        validateId(updateSportDto.getId());
        validateName(updateSportDto.getName());
        validateImage(icon);

        Binary imageBinary = new Binary(icon.getBytes());

        Optional<Sport> optionalSport = repository.findById(updateSportDto.getId());
        Sport foundSport = optionalSport.get();

        foundSport.setName(updateSportDto.getName());
        foundSport.setIcon(imageBinary);

        repository.save(foundSport);

        Optional<Sport> optionalUpdatedSport = repository.findById(updateSportDto.getId());
        Sport updatedSport = optionalUpdatedSport.get();

        return new SportDto(
                updatedSport.getId(),
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
                updatedSport.getId(),
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
                updatedSport.getId(),
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

    private void validateName(String name){
        if (name == null || name.isEmpty()){
            throw new SportNotFoundException("Invalid name entered.");
        }
    }

    private void validateImage(MultipartFile image) throws IOException {

        String contentType = image.getContentType();

        if (contentType == null || !contentType.equals("image/png")){
            throw new InvalidImageInSport("Invalid image entered.");
        }

        long imageMaxSize = 1024 * 1024;

        if (image.getSize() > imageMaxSize || image.getSize() == 0){
            throw new InvalidImageInSport("Invalid image entered.");
        }

        // Missing content validation
    }


}
