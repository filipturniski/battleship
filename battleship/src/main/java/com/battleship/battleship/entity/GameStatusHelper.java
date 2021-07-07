package com.battleship.battleship.entity;

public class GameStatusHelper {
    private GameStatusBoardHelper self;
    private GameStatusBoardHelper opponent;
    private GameHelper game;

    public GameStatusHelper(GameStatusBoardHelper self, GameStatusBoardHelper opponent,GameHelper game) {
        this.self = self;
        this.opponent = opponent;
        this.game = game;
    }

    public GameStatusHelper() {
    }

    public GameHelper getGame() {
        return game;
    }

    public void setGame(GameHelper game) {
        this.game = game;
    }

    public GameStatusBoardHelper getSelf() {
        return self;
    }

    public void setSelf(GameStatusBoardHelper self) {
        this.self = self;
    }

    public GameStatusBoardHelper getOpponent() {
        return opponent;
    }

    public void setOpponent(GameStatusBoardHelper opponent) {
        this.opponent = opponent;
    }
}