package com.battleship.battleship.repository;

import com.battleship.battleship.entity.BoatsOnBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoatsOnBoardRepository extends JpaRepository<BoatsOnBoard, Long> {
}
