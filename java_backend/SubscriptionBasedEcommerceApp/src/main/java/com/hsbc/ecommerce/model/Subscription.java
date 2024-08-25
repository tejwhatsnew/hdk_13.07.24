package com.hsbc.ecommerce.model;

import java.util.Date;

public class Subscription {
    private int id;
    private int userId;
    private String type;
    private Date startDate;
    private Date endDate;
    private boolean active;

    // Constructor
    public Subscription(int id, int userId, String type, Date startDate, Date endDate, boolean active) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.active = active;
    }

    public Subscription() {
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }

    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    // Additional methods
    public boolean isOngoing() { return active && endDate.after(new Date()); }
}

