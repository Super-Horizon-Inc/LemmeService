package com.super_horizon.lemmein.models;


public class Discount {
    
    private String by;
    private int amount;
    private int type;
    private int visitTimes;

    
    public Discount(String by, int amount, int type, int visitTimes) {
        this.by = by;
        this.amount = amount;
        this.type = type;
        this.visitTimes = visitTimes;
    }

    public String getBy () {
        return this.by;
    }

    public void setBy (String by) {
        this.by = by;
    }

    public int getAmount () {
        return this.amount;
    }

    public void setAmount (int amount) {
        this.amount = amount;
    }
    
    public int getType () {
        return this.type;
    }

    public void setType (int type) {
        this.type = type;
    }

    public int getVisitTimes () {
        return this.visitTimes;
    }

    public void setVisitTimes (int visitTimes) {
        this.visitTimes = visitTimes;
    }
}