package com.battleship.battleship.entity;

public class GameHelper {
    String player_turn;

    public GameHelper(String player_turn) {
        this.player_turn = "player-" + player_turn;
    }

    public GameHelper() {
    }

    public String getPlayer_turn() {
        return player_turn;
    }

    public void setPlayer_turn(String player_turn) {
        this.player_turn = player_turn;
    }
}
