package com.battleship.battleship.repository;

import com.battleship.battleship.entity.GameWar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameWarRepository extends JpaRepository<GameWar, Long> {
}
