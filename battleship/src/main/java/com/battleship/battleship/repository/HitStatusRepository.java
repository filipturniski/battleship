package com.battleship.battleship.repository;

import com.battleship.battleship.entity.HitStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HitStatusRepository extends JpaRepository<HitStatus, Long> {
}
