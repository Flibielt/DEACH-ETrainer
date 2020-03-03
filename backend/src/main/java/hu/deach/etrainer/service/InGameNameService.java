package hu.deach.etrainer.service;

import hu.deach.etrainer.dto.InGameNameDto;

import java.util.ArrayList;

public interface InGameNameService {

    Boolean save(InGameNameDto inGameNameDto);

    Boolean delete(Long playerId, Long gameId);

    Boolean update(InGameNameDto inGameNameDto);

    InGameNameDto findById(Long playerId, Long gameId);

    ArrayList<InGameNameDto> findAll();

}