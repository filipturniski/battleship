package com.battleship.battleship.entity;

import javax.persistence.*;

@Entity
@Table
public class boats {
    @Id
    @SequenceGenerator(
            name = "boats_sequence",
            sequenceName = "boats_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "boats_sequence"
    )
    private long id;
    private String boat;
    private long HP;
    private long size;
    private long LOT;

    public boats(String boat, long HP, long size, long LOT) {
        this.boat = boat;
        this.HP = HP;
        this.size = size;
        this.LOT = LOT;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBoat() {
        return boat;
    }

    public void setBoat(String boat) {
        this.boat = boat;
    }

    public long getHP() {
        return HP;
    }

    public void setHP(long HP) {
        this.HP = HP;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getLOT() {
        return LOT;
    }

    public void setLOT(long LOT) {
        this.LOT = LOT;
    }

    @Override
    public String toString() {
        return "boats{" +
                "id=" + id +
                ", boat='" + boat + '\'' +
                ", HP=" + HP +
                ", size=" + size +
                ", LOT=" + LOT +
                '}';
    }
}
