package com.battleship.battleship;

import com.battleship.battleship.entity.Boats;
import com.battleship.battleship.entity.Game;
import com.battleship.battleship.entity.Players;
import com.battleship.battleship.repository.GameRepository;
import com.battleship.battleship.repository.PlayerRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
public class BattleChallenge {

    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;
    @Autowired
    public BattleChallenge(GameRepository gameRepository, PlayerRepository playerRepository ){
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
    }

    @PostMapping(value = "/player/{player_id}/game")
    public ResponseEntity<String> getPlayerProfile(@PathVariable("player_id") String id) {
        Optional<Players> playerById = playerRepository.findPlayerByID(id);

        if(!playerById.isPresent())
            return new ResponseEntity<>(new JSONObject()
                    .put("error-code", "error.unknown-user-id")
                    .put("error-arg", id)
                    .toString(), HttpStatus.NOT_FOUND);

        else{

            return new ResponseEntity<>(getChallenge(id, getRandomOpponentID(playerRepository.getAllIds(id))), HttpStatus.CREATED);
        }
    }

    private Long getRandomOpponentID(List<Long> allIds) {
        Random rand = new Random();
        return allIds.get(rand.nextInt(allIds.size()));
    }

    private String getChallenge(String player_id, Long opponentID){


        Game game = new Game(
                Long.parseLong(player_id.split("-")[1]),
                Long.parseLong(opponentID.toString())
        );

        gameRepository.save(game);
        return game.toString();
    }


}
