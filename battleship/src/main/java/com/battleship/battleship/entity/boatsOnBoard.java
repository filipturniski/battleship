package com.battleship.battleship.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

public class boatsOnBoard {
    @Id
    @SequenceGenerator(
            name = "goatsOnBoars_sequence",
            sequenceName = "goatsOnBoars_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "goatsOnBoars_sequence"
    )
    private long id;
    private String gameId;
    private String playerId;
    private String boatId;
    private String currentHP;

    public boatsOnBoard(String gameId, String playerId, String boatId, String currentHP) {
        this.gameId = gameId;
        this.playerId = playerId;
        this.boatId = boatId;
        this.currentHP = currentHP;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getBoatId() {
        return boatId;
    }

    public void setBoatId(String boatId) {
        this.boatId = boatId;
    }

    public String getCurrentHP() {
        return currentHP;
    }

    public void setCurrentHP(String currentHP) {
        this.currentHP = currentHP;
    }

    @Override
    public String toString() {
        return "boatsOnBoard{" +
                "id=" + id +
                ", gameId='" + gameId + '\'' +
                ", playerId='" + playerId + '\'' +
                ", boatId='" + boatId + '\'' +
                ", currentHP='" + currentHP + '\'' +
                '}';
    }
}
