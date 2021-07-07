package com.battleship.battleship.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class GameStatusBoardHelper {
    private String player_id;
    private List<String> board;

    public GameStatusBoardHelper(String player_id, String board) {
        this.player_id = player_id;
        this.board =  Arrays.asList(board.substring(1, board.length() - 1).split(","));
    }

    public GameStatusBoardHelper() {}

    public GameStatusBoardHelper(Optional<GameWar> game) {
        this.player_id = "player-" + game.get().getPlayerId();
        this.board =  Arrays.asList(game.get().getBoard().substring(1, game.get().getBoard().length() - 1).split(","));
    }

    public GameStatusBoardHelper(Optional<GameWar> game, String string) {
        this.player_id = "player-" + game.get().getPlayerId();
        this.board =  replaceNumbersWithDots(Arrays.asList(game.get().getBoard().substring(1, game.get().getBoard().length() - 1).split(",")));
    }

    private List<String> replaceNumbersWithDots (List<String> input){
        List<String> output = new ArrayList<String>();
        for(int i = 0; i<input.size(); i++){
            String[] stringArray = input.get(i).split("(?!^)");
            String row = "";
            for(int j = 0; j<stringArray.length; j++){
                if(isNumeric(stringArray[j]))
                    row = row + ".";
                else
                    row = row + stringArray[j];
            }
            output.add(row);
        }

        return output;
    }


    private boolean  isNumeric(String value){
        try{
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public String getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(String player_id) {
        this.player_id = player_id;
    }

    public List<String> getBoard() {
        return board;
    }

    public void setBoard(List<String> board) {
        this.board = board;
    }
}
