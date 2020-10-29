package com.udacity.jdnd.course3.critter.user.entity;

import org.hibernate.annotations.Nationalized;

import javax.persistence.*;

@MappedSuperclass
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Nationalized
    private String name;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
