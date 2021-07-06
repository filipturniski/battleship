package com.battleship.battleship.repository;

import com.battleship.battleship.entity.GameStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameStatusRepository extends JpaRepository<GameStatus, Long> {
}
