package com.intech.server.db.models;

import javax.persistence.*;

@Entity
public class Content {

    private int id;
    private String name;
    private String text;

    public Content() {}
    public Content(String name, String text) {
        this.name = name;
        this.text = text;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    @Basic
    public String getName() {
        return name;
    }

    @Basic
    public String getText() {
        return text;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setText(String text) {
        this.text = text;
    }
}
