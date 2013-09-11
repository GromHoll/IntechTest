package com.intech.server.db.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Reader {

    private int id;
    private String username;
    private String password;
    private List<Content> contents;

    public Reader() {}
    public Reader(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    @Basic
    @Column(unique=true)
    public String getUsername() {
        return username;
    }

    @Basic
    public String getPassword() {
        return password;
    }

    @ManyToMany
    public List<Content> getContents() {
        return contents;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }
}
