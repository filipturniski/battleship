package com.battleship.battleship.entity;

import javax.persistence.*;

@Entity
@Table
public class players {
    @Id
    @SequenceGenerator(
            name = "players_sequence",
            sequenceName = "players_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "players_sequence"
    )
    private long id;
    private String player;
    private String name;
    private String email;

    public players(String player, String name, String email) {
        this.player = player;
        this.name = name;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "players{" +
                "id=" + id +
                ", player='" + player + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}