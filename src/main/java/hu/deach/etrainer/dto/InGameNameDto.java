package hu.deach.etrainer.dto;

import lombok.Data;

@Data
public class InGameNameDto {

    private Long playerId;
    private Long gameId;
    private String ign;

}