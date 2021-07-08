package com.battleship.battleship.repository;

import com.battleship.battleship.entity.Game;
import com.battleship.battleship.entity.Players;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository  extends JpaRepository<Game, Long> {
    @Query(value = "SELECT * FROM game WHERE id = ?1",
            nativeQuery = true)
    Optional<Game> findGameByID(String id);

    @Query(value = "SELECT * FROM game WHERE player_id = :id or opponent_id = id",
            nativeQuery = true)
    public List<Game> findGameByUser(@Param("id")Long id);

    @Query(value = "SELECT player_turn FROM game WHERE player_id = ?1 or opponent_Id = ?1 and id = ?2",
            nativeQuery = true)
    long getPlayerTurn(long palerId, long gameId);

}

