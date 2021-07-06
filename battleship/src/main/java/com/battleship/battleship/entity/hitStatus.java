package com.battleship.battleship.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class hitStatus {
    @Id
    private long id;
    private String hitStatus;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHitStatus() {
        return hitStatus;
    }

    public void setHitStatus(String hitStatus) {
        this.hitStatus = hitStatus;
    }

    public hitStatus(long id, String hitStatus) {
        this.id = id;
        this.hitStatus = hitStatus;
    }

    @Override
    public String toString() {
        return "hitStatus{" +
                "id=" + id +
                ", hitStatus='" + hitStatus + '\'' +
                '}';
    }
}
