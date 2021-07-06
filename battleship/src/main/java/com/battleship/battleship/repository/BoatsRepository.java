package com.battleship.battleship.repository;

import com.battleship.battleship.entity.Boats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoatsRepository extends JpaRepository<Boats, Long> {
}
