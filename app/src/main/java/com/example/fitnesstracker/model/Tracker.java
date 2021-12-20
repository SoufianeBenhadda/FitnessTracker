package com.example.fitnesstracker.model;

public class Tracker {
    private int id;
    private String userid;
    private int excerciseid;
    private String date;
    private double weight;
    private int reps;
    private double repmax;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public int getExcerciseid() {
        return excerciseid;
    }

    public void setExcerciseid(int excerciseid) {
        this.excerciseid = excerciseid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public double getRepmax() {
        return repmax;
    }

    public void setRepmax(double repmax) {
        this.repmax = repmax;
    }

    public Tracker(int id, String userid, int excerciseid, String date, double weight, int reps, double repmax) {
        this.id = id;
        this.userid = userid;
        this.excerciseid = excerciseid;
        this.date = date;
        this.weight = weight;
        this.reps = reps;
        this.repmax = repmax;
    }

    public Tracker() {
    }
}
