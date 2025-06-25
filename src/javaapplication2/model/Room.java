package javaapplication2.model;

import java.io.Serializable;

public class Room {
    private String roomId;
    private String roomName;
    private String roomType;
    private double dailyRate;
    private int capacity;
    private String funiture;

    // Default constructor
    public Room() {}

    // Constructor with parameters
    public Room(String roomId, String roomName, String roomType, double dailyRate, int capacity, String funiture) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.roomType = roomType;
        this.dailyRate = dailyRate;
        this.capacity = capacity;
        this.funiture = funiture;
    }

    public Room(String id) {
        this.roomId = id;
    }

    // Getters and Setters

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public double getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(double dailyRate) {
        this.dailyRate = dailyRate;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getFuniture() {
        return funiture;
    }

    public void setFuniture(String funiture) {
        this.funiture = funiture;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomId='" + roomId + '\'' +
                ", roomName='" + roomName + '\'' +
                ", roomType='" + roomType + '\'' +
                ", dailyRate=" + dailyRate +
                ", capacity=" + capacity +
                ", funiture='" + funiture + '\'' +
                '}';
    }

    public void showInformation() {
        System.out.printf("%-10s | %-20s | %-15s | %-10.2f | %-8d | %-15s%n",
                          roomId, roomName, roomType, dailyRate, capacity, funiture);
    }

    @Override
    public boolean equals(Object obj) {
        Room room = (Room) obj;
        return this.roomId.equals(room.roomId);
    }
}
