package com.battleship.battleship.repository;

import com.battleship.battleship.entity.GameWar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface GameWarRepository extends JpaRepository<GameWar, Long> {
    @Query(value = "SELECT * FROM game_war WHERE player_id = ?1 AND mach_id = ?2",
            nativeQuery = true)
    Optional<GameWar> findByPlayerIdAndMAchID(String playerId, String machId);

    @Modifying
    @Query(value = "update game_war wg set wg.board = ?1 where wg.id = ?2",
            nativeQuery = true)
    public void updateBoard(String sequenceNumber, long groupId);

}
