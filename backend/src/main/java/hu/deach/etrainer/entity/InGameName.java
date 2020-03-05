package hu.deach.etrainer.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "in_game_names")
@IdClass(IgnId.class)
public class InGameName {

    @Column
    private String ign;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id")
    private Player player;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Game game;
}
