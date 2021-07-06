package com.battleship.battleship.repository;
import com.battleship.battleship.entity.Players;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Players, Long> {

    @Query("SELECT p FROM Players p WHERE p.email = ?1")
    Optional<Players> findPlayerByEmail(String email);

    @Query(value = "SELECT * FROM Players WHERE id = SUBSTRING_INDEX(?1,'-',-1);",
            nativeQuery = true)
    Optional<Players> findPlayerByID(String id);

    @Query(value = "SELECT id FROM Players WHERE id <> SUBSTRING_INDEX(?1,'-',-1);",
            nativeQuery = true)
    List<Long> getAllIds(String id);
}
