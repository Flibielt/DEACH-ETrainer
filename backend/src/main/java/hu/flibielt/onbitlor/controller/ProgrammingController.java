package hu.flibielt.onbitlor.controller;

import hu.flibielt.onbitlor.dto.ProgrammingDto;
import hu.flibielt.onbitlor.dto.ProgrammingStatisticDto;
import hu.flibielt.onbitlor.model.ApiResponse;
import hu.flibielt.onbitlor.model.ProgrammingNameAvailability;
import hu.flibielt.onbitlor.model.ProgrammingRequest;
import hu.flibielt.onbitlor.model.ProgrammingResultRequest;
import hu.flibielt.onbitlor.security.CurrentUser;
import hu.flibielt.onbitlor.security.UserPrincipal;
import hu.flibielt.onbitlor.service.ProgrammingService;
import hu.flibielt.onbitlor.service.ProgrammingStatisticService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/api/programming")
public class ProgrammingController {

    @Autowired
    private ProgrammingService programmingService;

    @Autowired
    private ProgrammingStatisticService programmingStatisticService;

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

    @GetMapping("/checkNameAvailability")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ProgrammingNameAvailability checkNameAvailability(@RequestParam(value = "name") String name) {
        Boolean isAvailable = programmingService.existsByName(name);
        return new ProgrammingNameAvailability(isAvailable);
    }

    @PostMapping("/addResult")
    public ResponseEntity<?> addNewResult(@CurrentUser UserPrincipal userPrincipal, ProgrammingResultRequest request) {
        if (!programmingService.existsByName(request.getProgrammingName())) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "Cannot find the given programming competition"));
        }
        Long programmingId = programmingService.getId(request.getProgrammingName());

        ProgrammingStatisticDto programmingStatisticDto = new ProgrammingStatisticDto();
        programmingStatisticDto.setPlayerId(userPrincipal.getId());
        programmingStatisticDto.setProgrammingId(programmingId);
        programmingStatisticDto.setDate(new Date());
        programmingStatisticDto.setScore(request.getScore());

        ProgrammingStatisticDto savedResult = programmingStatisticService.save(programmingStatisticDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/result/{resultId}")
                .buildAndExpand(savedResult.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Created new programming result"));
    }

    @GetMapping("/result/{id}")
    public ProgrammingStatisticDto getProgrammingResult(@PathVariable(value = "id") Long id) {
        return programmingStatisticService.findById(id);
    }

    @GetMapping("/results/{programmingId}")
    public ArrayList<ProgrammingStatisticDto> getAllResultInCompetition(@PathVariable(value = "programmingId") Long programmingId) {
        return programmingStatisticService.findAll();
    }
}
