package com.battleship.battleship.repository;

import com.battleship.battleship.entity.GameWar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameWarRepository extends JpaRepository<GameWar, Long> {
    @Query(value = "SELECT * FROM game_war WHERE player_id = ?1 AND mach_id = ?2",
            nativeQuery = true)
    Optional<GameWar> findByPlayerIdAndMAchID(String playerId, String machId);
}
