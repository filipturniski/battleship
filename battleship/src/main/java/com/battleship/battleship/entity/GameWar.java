package com.battleship.battleship.entity;

import org.json.JSONObject;

import javax.persistence.*;
import java.lang.reflect.Array;

@Entity
@Table
public class GameWar {
    @Id
    @SequenceGenerator(
            name = "gameWar_sequence",
            sequenceName = "gameWar_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "gameWar_sequence"
    )
    private long id;
    private long playerId;
    private long machId;
    private String  board;

    public long getMachId() {
        return machId;
    }

    public void setMachId(long machId) {
        this.machId = machId;
    }

    public GameWar(long machId, long playerId) {
        this.machId = machId;
        this.playerId = playerId;
    }

    public GameWar() {
    }

    public GameWar(long machId, long playerId, String board) {
        this.machId = machId;
        this.playerId = playerId;
        this.board = board;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    @Override
    public String toString() {
        return "GameWar{" +
                "id=" + id +
                ", playerId=" + playerId +
                ", board='" + board + '\'' +
                '}';
    }
}
