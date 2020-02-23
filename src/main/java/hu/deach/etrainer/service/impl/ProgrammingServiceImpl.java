package hu.deach.etrainer.service.impl;

import com.google.common.collect.Lists;
import hu.deach.etrainer.dto.ProgrammingDto;
import hu.deach.etrainer.entity.Programming;
import hu.deach.etrainer.repository.ProgrammingRepository;
import hu.deach.etrainer.service.ProgrammingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class ProgrammingServiceImpl implements ProgrammingService {

    @Autowired
    private ProgrammingRepository programmingRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProgrammingDto save(ProgrammingDto programmingDto) {
        return null;
    }

    @Override
    public Boolean delete(ProgrammingDto programmingDto) {
        return null;
    }

    @Override
    public ProgrammingDto update(ProgrammingDto programmingDto) {
        return null;
    }

    @Override
    public ProgrammingDto findById(Long id) {
        return null;
    }

    @Override
    public ArrayList<ProgrammingDto> findByGameId(Long gameId) {
        return null;
    }

    @Override
    public ArrayList<ProgrammingDto> findAll() {
        return Lists.newArrayList(programmingRepository.findAll()).stream()
                .map(this::convertToDto)
                .collect(Collectors.toCollection(Lists::newArrayList));
    }

    private ProgrammingDto convertToDto(Programming programming) {
        return modelMapper.map(programming, ProgrammingDto.class);
    }

    private Programming convertToEntity(ProgrammingDto programmingDto) {
        Programming programming = modelMapper.map(programmingDto, Programming.class);

        if (programmingDto.getId() == null) {
            return null;
        }

        return programming;
    }

}