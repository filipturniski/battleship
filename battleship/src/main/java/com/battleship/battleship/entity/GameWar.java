package com.battleship.battleship.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.reflect.Array;

@Entity
@Table
public class GameWar {
    @Id
    private long id;
    private long gameId;
    private long playerId;
    private String  board;

    public GameWar(long id, long gameId, long playerId, String board) {
        this.id = id;
        this.gameId = gameId;
        this.playerId = playerId;
        this.board = board;
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

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    @Override
    public String toString() {
        return "gameWar{" +
                "id=" + id +
                ", gameId=" + gameId +
                ", playerId=" + playerId +
                ", board='" + board + '\'' +
                '}';
    }
}
