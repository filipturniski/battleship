package com.battleship.battleship;

import com.battleship.battleship.entity.*;
import com.battleship.battleship.repository.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
public class WarGame {

    private final BoatsRepository boatsRepository;
    private final PlayerRepository playerRepository;
    private final BoatsOnBoardRepository boatsOnBoardRepository;
    private final GameRepository gameRepository;
    private final GameWarRepository gameWarRepository;

    @Autowired
    public WarGame(BoatsRepository boatsRepository, PlayerRepository playerRepository, BoatsOnBoardRepository boatsOnBoardRepository, GameRepository gameRepository, GameWarRepository gameWarRepository){
        this.boatsRepository = boatsRepository;
        this.playerRepository = playerRepository;
        this.boatsOnBoardRepository = boatsOnBoardRepository;
        this.gameRepository = gameRepository;
        this.gameWarRepository = gameWarRepository;
    }

    @RequestMapping(path = "/getBoats/list/{mach_id}/{player_id}")
    public ResponseEntity<String> getBoatsList(@PathVariable("mach_id") String machId, @PathVariable("player_id") String playerId){

        List<Boats> boatsList  = boatsRepository.findAll();

        List<Boats> outputList = new ArrayList<Boats>();

        List<BoatsOnBoard> boatsOnBoardList = new ArrayList<BoatsOnBoard>();

        Optional<Players> playerByID = playerRepository.findPlayerByID(playerId.split("-")[1]);

        Optional<Game> machByID = gameRepository.findById(Long.parseLong(machId.split("-")[1]));

        if(!playerByID.isPresent())
            return new ResponseEntity<>(new JSONObject()
                    .put("error-code", "error.username-does-not-exists")
                    .put("error-arg", playerId)
                    .toString(), HttpStatus.CONFLICT);
        else if(!machByID.isPresent())
            return new ResponseEntity<>(new JSONObject()
                    .put("error-code", "error.mech-does-not-exists")
                    .put("error-arg", machId)
                    .toString(), HttpStatus.CONFLICT);

        long id = 0;
        for (int i = 0; i<boatsList.size(); i++) {
            for(int j = 0; j<boatsList.get(i).getLOT(); j++){
                id++;
                outputList.add(new Boats(id, boatsList.get(i).getBoat(), boatsList.get(i).getHP(), boatsList.get(i).getSize(), 1L) );
                boatsOnBoardList.add(new BoatsOnBoard(Long.parseLong(machId.split("-")[1]) , Long.parseLong(playerId.split("-")[1]) , id, boatsList.get(i).getHP()));
            }
        }

        boatsOnBoardRepository.saveAll(boatsOnBoardList);
        return new ResponseEntity<>(new JSONObject()
                .put("boats", outputList)
                .toString(), HttpStatus.OK);
    }

    @PostMapping(path = "/setShips")
    public ResponseEntity<String> setBoard(@RequestBody BoardHelper boardHelper){

        Optional<Players> playerByEmail = playerRepository.findPlayerByLongID(boardHelper.getPlayerId());
        Optional<Game> machByID = gameRepository.findById(boardHelper.getMachId());
        if(!playerByEmail.isPresent())
            return new ResponseEntity<>(new JSONObject()
                    .put("error-code", "error.username-does-not-exists")
                    .put("error-arg", boardHelper.getPlayerId())
                    .toString(), HttpStatus.CONFLICT);

        else if(!machByID.isPresent())
            return new ResponseEntity<>(new JSONObject()
                    .put("error-code", "game-does-not-exists")
                    .put("error-arg", boardHelper.getMachId())
                    .toString(), HttpStatus.CONFLICT);

        else{
           if(boardHasErrors(boardHelper.getBoard()))
                return new ResponseEntity<>(new JSONObject()
                        .put("error-code", "game-does-support-board-config")
                        .put("error-arg", boardHelper.getBoard())
                        .toString(), HttpStatus.CONFLICT);

            gameWarRepository.save(boardHelper.toWarGame());
            return new ResponseEntity<>("", HttpStatus.CREATED);
        }

    }

    private boolean boardHasErrors(List<String> board) {
        if(board.size() != 10){
            return true;
        }

        for(int i = 0; i<board.size(); i++){
            try{
                String[] stringArray = board.get(i).split("(?!^)");
                String[] stringArrayUp = board.get(i+1).split("(?!^)");
                String[] stringArrayDown = board.get(i-1).split("(?!^)");
                if(stringArray.length != 10){
                    return true;
                }

                for(int j = 0; j<stringArray.length; j++){
                    try{
                        if(isNumeric(stringArray[j]) ){
                            if(isNumeric(stringArray[j+1]) && (stringArray[j] != stringArray[j+1]) )
                                return true;
                            if( isNumeric(stringArray[j-1]) && (stringArray[j] != stringArray[j-1]) )
                                return true;
                            if(isNumeric(stringArray[j+1]) && (stringArray[j] != stringArrayUp[j]) )
                                return true;
                            if(isNumeric(stringArray[j+1]) && (stringArray[j] != stringArrayDown[j]) )
                                return true;
                        }

                    }catch (Exception e){}
                }
            }catch (Exception e){}
        }
        return false;
    }

    private boolean  isNumeric(String value){
        try{
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @RequestMapping(path = "/player/{player_id}/game/{game_id}")
    public ResponseEntity<String>  getGameStatus (@PathVariable("player_id") String playerId, @PathVariable("game_id") String machId){
        Optional<Players> playerById = playerRepository.findPlayerByLongID(Long.parseLong(playerId.replaceAll("[^0-9]","")));
        Optional<Game> machByID = gameRepository.findById(Long.parseLong(machId.replaceAll("[^0-9]","")));
        if(!playerById.isPresent())
            return new ResponseEntity<>(new JSONObject()
                    .put("error-code", "error.unknown-user-id")
                    .put("error-arg", playerId)
                    .toString(), HttpStatus.NOT_FOUND);

        else if(!machByID.isPresent())
            return new ResponseEntity<>(new JSONObject()
                    .put("error-code", "error.unknown-game-id")
                    .put("error-arg", machId)
                    .toString(), HttpStatus.NOT_FOUND);
        else{

            return new ResponseEntity<>(  new JSONObject(new GameStatusHelper(new GameStatusBoardHelper(gameWarRepository.findByPlayerIdAndMAchID(playerId, machId)),
                    new GameStatusBoardHelper(gameWarRepository.findByPlayerIdAndMAchID(String.valueOf(gameRepository.findGameByID(playerId).get().getOpponentId()), machId), "opponent"),
                    new GameHelper(String.valueOf(gameRepository.findGameByID(playerId).get().getPlayerTurn())))).toString(), HttpStatus.OK);

        }

    }

    @RequestMapping(path = "/player/{player_id}/game/list")
    public ResponseEntity<String>  getGameList (@PathVariable("player_id") String playerId){
        Optional<Players> playerById = playerRepository.findPlayerByLongID(Long.parseLong(playerId.replaceAll("[^0-9]","")));
        List<Game> playerGames= gameRepository.findGameByUser(Long.parseLong(playerId.replaceAll("[^0-9]","")));
        if(!playerById.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if(playerGames.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else{
            return new ResponseEntity<>(new JSONObject()
                    .put("games", playerGames)
                    .toString(), HttpStatus.OK);

        }

    }

    @PostMapping(path = "/player/{player_id}/game/{game_id}")
    public ResponseEntity<String>  setSalvo (@PathVariable("player_id") String playerId,
                                             @PathVariable("game_id") String machId,
                                             @RequestBody String salvo){
        Optional<Players> playerById = playerRepository.findPlayerByLongID(Long.parseLong(playerId.replaceAll("[^0-9]","")));
        Optional<Game> machByID = gameRepository.findById(Long.parseLong(machId.replaceAll("[^0-9]","")));
        String[] salvoSlit = salvo.replaceAll("[^A-Za-z0-9,]","").replaceAll("salvo","").split(",");

        if(!playerById.isPresent() || !machByID.isPresent())
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);

        else if(isNotPlayersTurn(Long.parseLong(playerId.replaceAll("[^0-9]","")),
        Long.parseLong(machId.replaceAll("[^0-9]",""))))
            return new ResponseEntity<>( HttpStatus.FORBIDDEN);
        else{
            return new ResponseEntity<>(new JSONObject()
                    .put("salvo", getSalvo(salvoSlit, machId, playerId))
                    .toString(),
                    HttpStatus.OK);

        }

    }

    private HashMap<String, String> getSalvo(String[] listSalvo, String machId, String playerId) {
        HashMap<String, String> OutputMap = new HashMap<String, String>();

        Optional<GameWar> gameWorBoard = gameWarRepository.findByPlayerIdAndMAchID(String.valueOf(gameRepository.findGameByID(playerId).get().getOpponentId()), machId);

        JSONArray jsonArray = new JSONArray(gameWorBoard.get().getBoard());

        String[][] board = jsonToArray(jsonArray);

        for(int i = 0; i < listSalvo.length; i++){
            OutputMap.put(listSalvo[i],isHit(board, listSalvo[i], gameWorBoard));
            int [][] salvoCoordinatesValue = salvoCoordinates(listSalvo[i]);
            updateBoard(board, salvoCoordinatesValue, gameWorBoard);
        }


        return OutputMap;
    }

    private String isHit(String[][] board, String s, Optional<GameWar> gameWorBoard) {
        int [][] salvoCoordinatesValue = salvoCoordinates(s);
        //updateBoard(board, salvoCoordinatesValue, gameWorBoard);
        if(isNumeric(board[salvoCoordinatesValue[0][0]][salvoCoordinatesValue[0][1]]))
            if(boatsOnBoardRepository.findBoatByID(Long.parseLong(board[salvoCoordinatesValue[0][0]][salvoCoordinatesValue[0][1]]))
                    .get().getCurrentHP() -1 < 0)
                return "KILL";
            else
                return "HIT";
        else
            return "MISS";



    }

    private void updateBoard(String[][] board, int[][] salvoCoordinatesValue, Optional<GameWar> gameWorBoard) {
        System.out.println("board[salvoCoordinatesValue[0][0]][salvoCoordinatesValue[0][1]]: " + board[salvoCoordinatesValue[0][0]][salvoCoordinatesValue[0][1]]);
        if(isNumeric(board[salvoCoordinatesValue[0][0]][salvoCoordinatesValue[0][1]]) ){
            boatsOnBoardRepository.boatHit(Long.parseLong(board[salvoCoordinatesValue[0][0]][salvoCoordinatesValue[0][1]]));
            board[salvoCoordinatesValue[0][0]][salvoCoordinatesValue[0][1]] ="O";
        }
        else if (board[salvoCoordinatesValue[0][0]][salvoCoordinatesValue[0][1]] == "X"){}
        else
            board[salvoCoordinatesValue[0][0]][salvoCoordinatesValue[0][1]] = "X";

        gameWarRepository.updateBoard(toStringBoard(board).toString(),gameWorBoard.get().getId() );

    }

    private List<String> toStringBoard(String[][] board) {
        List<String> newBoard = new ArrayList<>();
        for(int i = 0; i<10;i++){
            String row = "";
            for(int j = 0; j<10;j++){
                    row = row + board[i][j];

            }
            newBoard.add(row);
        }
        System.out.println("newBoard: " + newBoard);

        return newBoard;
    }

    int [][] salvoCoordinates(String salvo){
        int [][] hitCoordinates = new int [1][2];
        String[] salvoSplit = salvo.split("x");
        hitCoordinates[0][0] = Integer.parseInt(salvoSplit[0])-1;
        switch(salvoSplit[1]){
            case "A":
                hitCoordinates[0][1] = 0;
                break;
            case "B":
                hitCoordinates[0][1] = 1;
                break;
            case "C":
                hitCoordinates[0][1] = 2;
                break;
            case "D":
                hitCoordinates[0][1] = 3;
                break;
            case "E":
                hitCoordinates[0][1] = 4;
                break;
            case "F":
                hitCoordinates[0][1] = 5;
                break;
            case "G":
                hitCoordinates[0][1] = 6;
                break;
            case "H":
                hitCoordinates[0][1] = 7;
                break;
            case "I":
                hitCoordinates[0][1] = 8;
                break;
            case "J":
                hitCoordinates[0][1] = 9;
                break;
        }
        return hitCoordinates;
    }

    private String[][] jsonToArray(JSONArray jsonArray) {
        String[][] outputValue = new String[10][10];
        for(int i = 0; i <jsonArray.length();i++){
            String[] stringArray = jsonArray.get(i).toString().split("(?!^)");
            for(int j = 0; j <stringArray.length;j++){
                outputValue[i][j] = stringArray[j];
            }
        }
        return outputValue;
    }

    private boolean isNotPlayersTurn(long palerId, long gameId) {
        if(palerId == gameRepository.getPlayerTurn(palerId,gameId))
            return false;
        else
            return true;
    }
}
