package hu.deach.etrainer.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JoinedGame {

    private Long id;
    private String name;
    private String ign;

}