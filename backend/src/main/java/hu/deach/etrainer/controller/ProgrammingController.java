package hu.deach.etrainer.controller;

import hu.deach.etrainer.dto.ProgrammingDto;
import hu.deach.etrainer.model.ApiResponse;
import hu.deach.etrainer.model.ProgrammingRequest;
import hu.deach.etrainer.service.ProgrammingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;

@Slf4j
@RestController
@RequestMapping("/api/programming")
public class ProgrammingController {

    @Autowired
    private ProgrammingService programmingService;

    @PostMapping
    public ResponseEntity<?> addProgramming(@RequestBody ProgrammingRequest request) {
        ProgrammingDto programmingDto = programmingService.save(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{gameId}")
                .buildAndExpand(programmingDto.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Programming successfully created"));
    }

    @GetMapping("/all")
    public ArrayList<ProgrammingDto> getAllProgramming() {
        return programmingService.findAll();
    }

    @GetMapping("/{programmingId}")
    public ProgrammingDto getProgrammingById(@PathVariable(value = "programmingId") Long programmingId) {
        return programmingService.findById(programmingId);
    }
}