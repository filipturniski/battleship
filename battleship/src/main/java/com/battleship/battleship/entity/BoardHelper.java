package com.battleship.battleship.entity;

import org.json.JSONArray;

import javax.persistence.GeneratedValue;
import java.util.List;

public class BoardHelper {
    private long machId;
    private long playerId;
    List<String> board;

    public BoardHelper(long machId, long playerId, List<String> board) {
        this.machId = machId ;
        this.playerId = playerId ;
        this.board = board;
    }

    public BoardHelper(String machId, String playerId, List<String> board) {
        this.machId = Long.parseLong(machId.split("-")[1]) ;
        this.playerId = Long.parseLong(playerId.split("-")[1]) ;
        this.board = board;
    }

    public BoardHelper() {
    }

    public long getMachId() {
        return machId;
    }

    public void setMachId(long machId) {
        this.machId = machId;
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public List<String> getBoard() {
        return board;
    }

    public void setBoard(List<String> board) {
        this.board = board;
    }

    @Override
    public String toString() {
        return "BoardHelper{" +
                "machId=" + machId +
                ", playerId=" + playerId +
                ", board=" + board +
                '}';
    }

    public GameWar toWarGame(){
        return new GameWar(machId,playerId, board.toString());
    }
}
