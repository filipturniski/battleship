package com.battleship.battleship.config;

import com.battleship.battleship.entity.Boats;
import com.battleship.battleship.entity.GameStatus;
import com.battleship.battleship.repository.BoatsRepository;
import com.battleship.battleship.repository.GameStatusRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class BoatsConfig {
    @Bean
    CommandLineRunner commandLineRunnerBoatConfig(BoatsRepository repository){
        return args -> {
            Boats battleship = new Boats(
                    1L,
                    "Battleship",
                    4L,
                    4L,
                    1L
            );
            Boats destroyer = new Boats(
                    2L,
                    "Destroyer",
                    3L,
                    3L,
                    2L
            );
            Boats submarine = new Boats(
                    3L,
                    "Submarine",
                    2L,
                    2L,
                    3L
            );
            Boats patrolCraft = new Boats(
                    4L,
                    "Patrol Craft",
                    1L,
                    1L,
                    4L
            );

            repository.saveAll(List.of(battleship, destroyer, submarine, patrolCraft));
        };

    }
}
