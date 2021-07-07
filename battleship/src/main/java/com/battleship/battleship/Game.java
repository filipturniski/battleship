package com.battleship.battleship;

import com.battleship.battleship.entity.Boats;
import com.battleship.battleship.entity.BoatsOnBoard;
import com.battleship.battleship.entity.Players;
import com.battleship.battleship.repository.BoatsOnBoardRepository;
import com.battleship.battleship.repository.BoatsRepository;
import com.battleship.battleship.repository.PlayerRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

@RestController
public class Game {

    private final BoatsRepository boatsRepository;
    private final PlayerRepository playerRepository;
    private final BoatsOnBoardRepository boatsOnBoardRepository;

    @Autowired
    public Game(BoatsRepository boatsRepository, PlayerRepository playerRepository, BoatsOnBoardRepository boatsOnBoardRepository){
        this.boatsRepository = boatsRepository;
        this.playerRepository = playerRepository;
        this.boatsOnBoardRepository = boatsOnBoardRepository;
    }

    @RequestMapping(path = "/getBoats/list/{mach_id}/{player_id}")
    public List<Boats> getBoatsList(@PathVariable("mach_id") String machId, @PathVariable("player_id") String playerId){

        List<Boats> boatsList  = boatsRepository.findAll();

        List<Boats> outputList = new ArrayList<Boats>();

        List<BoatsOnBoard> boatsOnBoardList = new ArrayList<BoatsOnBoard>();



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


    @PostMapping(path = "/getBoats/setShips")
    public ResponseEntity<String> getAvailableBoatsList(@RequestBody Players player){

        Optional<Players> playerByEmail = playerRepository.findPlayerByEmail(player.getEmail());

        if(playerByEmail.isPresent())
            return new ResponseEntity<>(new JSONObject()
                    .put("error-code", "error.username-already-taken")
                    .put("error-arg", "pero.peric@ag04.com")
                    .toString(), HttpStatus.CONFLICT);

        else{
            playerRepository.save(player);
            return new ResponseEntity<>("", HttpStatus.CREATED);
        }

    }

}
