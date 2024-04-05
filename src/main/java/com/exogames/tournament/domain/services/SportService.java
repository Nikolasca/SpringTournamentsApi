package com.exogames.tournament.domain.services;

import com.exogames.tournament.domain.dtos.CreateSportDto;
import com.exogames.tournament.domain.dtos.SportDto;
import com.exogames.tournament.domain.dtos.UpdateSportDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SportService {

    SportDto createSport (CreateSportDto createSportDto);
    List<SportDto> getAllSports();
    List<SportDto> getAllActiveSports();
    SportDto getSportById(String id);
    SportDto updateSport(UpdateSportDto updateSportDto);
    SportDto deactivateSport(String id);
    SportDto activateSport(String id);
    void deleteSport(String id);
}
