package hu.deach.etrainer.service.impl;

import com.google.common.collect.Lists;
import hu.deach.etrainer.dto.TestDto;
import hu.deach.etrainer.entity.Test;
import hu.deach.etrainer.repository.TestRepository;
import hu.deach.etrainer.service.TestService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

public class TestServiceImpl implements TestService {

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Boolean save(TestDto testDto) {
        long count = testRepository.count();
        Test test = testRepository.save(convertToEntity(testDto));
        return count < testRepository.count() && test.getId() != null;
    }

    @Override
    public Boolean delete(TestDto testDto) {
        long count = testRepository.count();
        testRepository.delete(convertToEntity(testDto));
        return count > testRepository.count();
    }

    @Override
    public Boolean update(TestDto testDto) {
        Test test = testRepository.save(convertToEntity(testDto));
        return convertToDto(test).equals(testDto);
    }

    @Override
    public TestDto findById(Long id) {
        Optional<Test> test = testRepository.findById(id);
        return test.map(this::convertToDto).orElse(null);
    }

    @Override
    public TestDto findByName(String name) {
        return convertToDto(testRepository.findByName(name));
    }

    @Override
    public ArrayList<TestDto> findAll() {
        return Lists.newArrayList(testRepository.findAll()).stream()
                .map(this::convertToDto)
                .collect(Collectors.toCollection(Lists::newArrayList));
    }

    private TestDto convertToDto(Test test) {
        return modelMapper.map(test, TestDto.class);
    }

    private Test convertToEntity(TestDto testDto) {
        return modelMapper.map(testDto, Test.class);
    }
}