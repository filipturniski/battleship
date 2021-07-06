package com.battleship.battleship.entity;

import javax.persistence.*;

@Entity
@Table
public class game {
    @Id
    @SequenceGenerator(
            name = "game_sequence",
            sequenceName = "game_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "game_sequence"
    )
    private long id;
    private String fixText;
    private String playerId;
    private String opponentId;
    private String startingPlayer;
    private String gameStatus;

    public game(String fixText, String playerId, String opponentId, String startingPlayer, String gameStatus) {
        this.fixText = fixText;
        this.playerId = playerId;
        this.opponentId = opponentId;
        this.startingPlayer = startingPlayer;
        this.gameStatus = gameStatus;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFixText() {
        return fixText;
    }

    public void setFixText(String fixText) {
        this.fixText = fixText;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getOpponentId() {
        return opponentId;
    }

    public void setOpponentId(String opponentId) {
        this.opponentId = opponentId;
    }

    public String getStartingPlayer() {
        return startingPlayer;
    }

    public void setStartingPlayer(String startingPlayer) {
        this.startingPlayer = startingPlayer;
    }

    public String getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(String gameStatus) {
        this.gameStatus = gameStatus;
    }

    @Override
    public String toString() {
        return "game{" +
                "id=" + id +
                ", fixText='" + fixText + '\'' +
                ", playerId='" + playerId + '\'' +
                ", opponentId='" + opponentId + '\'' +
                ", startingPlayer='" + startingPlayer + '\'' +
                ", gameStatus='" + gameStatus + '\'' +
                '}';
    }
}
