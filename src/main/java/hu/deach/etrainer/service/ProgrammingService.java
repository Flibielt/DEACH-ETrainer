package hu.deach.etrainer.service;

import hu.deach.etrainer.dto.ProgrammingDto;

import java.util.ArrayList;

public interface ProgrammingService {

    ProgrammingDto save(ProgrammingDto programmingDto);

    Boolean delete(ProgrammingDto programmingDto);

    ProgrammingDto update(ProgrammingDto programmingDto);

    ProgrammingDto findById(Long id);

    ArrayList<ProgrammingDto> findByGameId(Long gameId);

    ArrayList<ProgrammingDto> findAll();

}