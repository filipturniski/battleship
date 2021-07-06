package com.battleship.battleship;

import com.battleship.battleship.repository.PlayerRepository;
import com.battleship.battleship.entity.Players;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BattleshipPlayer {
    private final PlayerRepository playerRepository;

    @Autowired
    public BattleshipPlayer(PlayerRepository playerRepository){
        this.playerRepository = playerRepository;
    }

    @RequestMapping(path = "/player/list")
    public List<Players> getPlayers(){
        return playerRepository.findAll();
    }

    @PostMapping(path = "/player")
    public ResponseEntity<String> addPlayer(@RequestBody Players player){
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

    @RequestMapping(value = "/player/{player_id}")
    public ResponseEntity<String>  getPlayerProfile(@PathVariable("player_id") String id) {

        Optional<Players> playerById = playerRepository.findPlayerByID(id);
        if(playerById
                .isPresent())
        return new ResponseEntity<>(
                new JSONObject()
                        .put("name", playerById.get().getName())
                        .put("email", playerById.get().getEmail())
                        .toString()
                , HttpStatus.OK);
        else
            return new ResponseEntity<>(""
                    , HttpStatus.NOT_FOUND);
    }

}
