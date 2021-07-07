package com.battleship.battleship.entity;

import javax.persistence.*;

@Entity
@Table
public class BoatsOnBoard {
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
    private long gameId;
    private long playerId;
    private long boatId;
    private long currentHP;

    public BoatsOnBoard(long gameId, long playerId, long boatId, long currentHP) {
        this.gameId = gameId;
        this.playerId = playerId;
        this.boatId = boatId;
        this.currentHP = currentHP;
    }

    public BoatsOnBoard() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public long getBoatId() {
        return boatId;
    }

    public void setBoatId(long boatId) {
        this.boatId = boatId;
    }

    public long getCurrentHP() {
        return currentHP;
    }

    public void setCurrentHP(long currentHP) {
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
