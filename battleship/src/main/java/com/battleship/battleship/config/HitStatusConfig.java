package com.battleship.battleship.config;

import com.battleship.battleship.entity.HitStatus;
import com.battleship.battleship.repository.HitStatusRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration

public class HitStatusConfig {
    @Bean
    CommandLineRunner commandLineRunnerHitStatus(HitStatusRepository repository){
        return args -> {
            HitStatus hit = new HitStatus(
                    1L,
                    "HIT"
            );
            HitStatus kill = new HitStatus(
                    2L,
                    "KILL"
            );
            HitStatus lost = new HitStatus(
                    3L,
                    "MISS"
            );
            repository.saveAll(List.of(hit, kill, lost));
        };

    }
}
