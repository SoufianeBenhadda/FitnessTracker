package com.example.fitnesstracker.model;

import android.util.Log;

import java.io.Serializable;

public class Excercise implements Serializable {
    private int id;
    private String title;
    private String description;
    private String image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Excercise(){

    }

    public Excercise(int id, String name, String description, String image) {
        Log.d("+++++++id",""+id);
        Log.d("+++++++title",name);
        Log.d("+++++++desc",description);
        Log.d("+++++++image",image);

        this.id = id;
        this.title = name;
        this.description = description;
        this.image = image;
    }
}
