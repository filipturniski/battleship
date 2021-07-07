package com.battleship.battleship.repository;

import com.battleship.battleship.entity.Game;
import com.battleship.battleship.entity.Players;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameRepository  extends JpaRepository<Game, Long> {
    @Query(value = "SELECT * FROM game WHERE id = ?1",
            nativeQuery = true)
    Optional<Game> findGameByID(String id);
}
