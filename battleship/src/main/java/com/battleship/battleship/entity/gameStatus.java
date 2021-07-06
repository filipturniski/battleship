package com.battleship.battleship.entity;

import javax.persistence.*;

@Entity
@Table
public class gameStatus {
    @Id
    private long id;
    private String gameStatusValue;

    public gameStatus(long id, String gameStatusValue) {
        this.id = id;
        this.gameStatusValue = gameStatusValue;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGameStatusValue() {
        return gameStatusValue;
    }

    public void setGameStatusValue(String gameStatusValue) {
        this.gameStatusValue = gameStatusValue;
    }

    @Override
    public String toString() {
        return "gameStatus{" +
                "id=" + id +
                ", gameStatusValue='" + gameStatusValue + '\'' +
                '}';
    }
}
