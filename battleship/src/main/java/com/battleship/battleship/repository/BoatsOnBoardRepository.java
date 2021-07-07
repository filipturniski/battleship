package com.battleship.battleship.repository;

import com.battleship.battleship.entity.BoatsOnBoard;
import com.battleship.battleship.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface BoatsOnBoardRepository extends JpaRepository<BoatsOnBoard, Long> {
    @Query(value = "SELECT * FROM boats_on_board WHERE id = ?1",
            nativeQuery = true)
    Optional<BoatsOnBoard> findBoatByID(long id);

    @Modifying
    @Query(value = "update boats_on_board bob set bob.currenthp = bob.currenthp-1 where bob.id = ?1",
            nativeQuery = true)
    public void boatHit(long boatId);
}
