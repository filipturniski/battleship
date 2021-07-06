package com.battleship.battleship.config;

import com.battleship.battleship.entity.GameStatus;
import com.battleship.battleship.repository.GameStatusRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class GameStatusConfig {
    @Bean
    CommandLineRunner commandLineRunnerGameStatus(GameStatusRepository repository){
        return args -> {
            GameStatus inProgress = new GameStatus(
                    1L,
                    "IN_PROGRESS"
            );
            GameStatus won = new GameStatus(
                    2L,
                    "WON"
            );
            GameStatus lost = new GameStatus(
                    3L,
                    "LOST"
            );
            repository.saveAll(List.of(inProgress, won, lost));
        };

    }
}
