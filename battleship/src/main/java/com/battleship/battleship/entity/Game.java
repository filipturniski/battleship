package com.battleship.battleship.entity;

import org.json.JSONObject;

import javax.persistence.*;

@Entity
@Table
public class Game {
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
    private long playerId;
    private long opponentId;
    private long startingPlayer;
    private String gameStatus;

    public Game(String fixText, long playerId, long opponentId, long startingPlayer, String gameStatus) {
        this.fixText = fixText;
        this.playerId = playerId;
        this.opponentId = opponentId;
        this.startingPlayer = startingPlayer;
        this.gameStatus = gameStatus;
    }

    public Game(long playerId, long opponentId) {
        this.playerId = playerId;
        this.opponentId = opponentId;
        this.startingPlayer = playerId;
        this.gameStatus = "IN_PROGRESS";
    }

    public Game() {
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

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public long getOpponentId() {
        return opponentId;
    }

    public void setOpponentId(long opponentId) {
        this.opponentId = opponentId;
    }

    public long getStartingPlayer() {
        return startingPlayer;
    }

    public void setStartingPlayer(long startingPlayer) {
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
        return new JSONObject()
                .put("player_id", "player-" + getPlayerId())
                .put("opponent_id", "player-" + getOpponentId())
                .put("game_id", "match-" + getId())
                .put("starting", "player-" + getStartingPlayer())
                .toString();
    }
}
