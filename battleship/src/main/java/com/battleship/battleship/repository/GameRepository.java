package com.battleship.battleship.repository;

import com.battleship.battleship.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository  extends JpaRepository<Game, Long> {
}
