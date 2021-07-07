package com.battleship.battleship;

import com.battleship.battleship.entity.*;
import com.battleship.battleship.repository.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;

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
    public List<Boats> getBoatsList(@PathVariable("mach_id") String machId, @PathVariable("player_id") String playerId){

        List<Boats> boatsList  = boatsRepository.findAll();

        List<Boats> outputList = new ArrayList<Boats>();

        List<BoatsOnBoard> boatsOnBoardList = new ArrayList<BoatsOnBoard>();
/*
        Optional<Players> playerByID = playerRepository.findPlayerByID(playerId.split("-")[1]);

        Optional<Game> machByID = gameRepository.findById(Long.parseLong(machId.split("-")[1]));

        if(!playerByID.isPresent())
            return null;
        else if(!machByID.isPresent())
            return null;
*/
        long id = 0;
        for (int i = 0; i<boatsList.size(); i++) {
            for(int j = 0; j<boatsList.get(i).getLOT(); j++){
                id++;
                outputList.add(new Boats(id, boatsList.get(i).getBoat(), boatsList.get(i).getHP(), boatsList.get(i).getSize(), 1L) );
                boatsOnBoardList.add(new BoatsOnBoard(Long.parseLong(machId.split("-")[1]) , Long.parseLong(playerId.split("-")[1]) , id, boatsList.get(i).getHP()));
            }
        }

        boatsOnBoardRepository.saveAll(boatsOnBoardList);
        return outputList;
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
        Optional<Players> playerByEmail = playerRepository.findPlayerByLongID(Long.parseLong(playerId.replaceAll("[^0-9]","")));
        Optional<Game> machByID = gameRepository.findById(Long.parseLong(machId.replaceAll("[^0-9]","")));
        if(!playerByEmail.isPresent())
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
            return new ResponseEntity<>(  new JSONObject()
                    .put("games", playerGames)
                    .toString(), HttpStatus.OK);

        }

    }
}
