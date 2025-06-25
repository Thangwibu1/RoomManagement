package javaapplication2.model;

import javaapplication2.storage.GuestList;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;

public class Reservation implements Serializable {
    private String reservedId;
    private String nationalIdNumber;
    private String roomId;
    private int rentalDays;
    private String startDate;
    private String endDate;
    private String nameOfCoTenant;
    private static final long serialVersionUID = 3348549654243379713L;

    // Default constructor
    public Reservation() {
    }
    // Constructor with parameters
    public boolean availableToDelete(Reservation reservation) {
        boolean check = false;
        String reservedDateString = reservation.getStartDate();
        LocalDate currentDate = LocalDate.now();
        LocalDate reservedDate = LocalDate.parse(reservedDateString.substring(6) + "-" + reservedDateString.substring(3, 5) + "-" + reservedDateString.substring(0, 2));
        if (!reservedDate.isAfter(currentDate) || reservedDate.isEqual(currentDate)) {
            check = false;
            System.out.println("Can not delete reservation because it is in the future or today."); // Reservation can be deleted
        } else {
            check = true; // Reservation cannot be deleted
            
        }
        return check;
    }

    public Reservation(String reservedId, String nationalIdNumber, String roomId, int rentalDays, String startDate, String endDate, String nameOfCoTenant) {
        this.reservedId = reservedId;
        this.nationalIdNumber = nationalIdNumber;
        this.roomId = roomId;
        this.rentalDays = rentalDays;
        this.startDate = startDate;
        this.endDate = endDate;
        this.nameOfCoTenant = nameOfCoTenant;
    }
    // Constructor with reservedId
    public Reservation(String reservedId) {
        this.reservedId = reservedId;
    }
    // Getters and Setters

    public String getReservedId() {
        return reservedId;
    }

    public void setReservedId(String reservedId) {
        this.reservedId = reservedId;
    }

    public String getNationalIdNumber() {
        return nationalIdNumber;
    }

    public void setNationalIdNumber(String nationalIdNumber) {
        this.nationalIdNumber = nationalIdNumber;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public int getRentalDays() {
        return rentalDays;
    }

    public void setRentalDays(int rentalDays) {
        this.rentalDays = rentalDays;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getNameOfCoTenant() {
        return nameOfCoTenant;
    }

    public void setNameOfCoTenant(String nameOfCoTenant) {
        this.nameOfCoTenant = nameOfCoTenant;
    }

    public void showInformation() throws IOException {
        System.out.println("Reservation ID: " + reservedId);
        System.out.println("National ID Number: " + nationalIdNumber);
        GuestList readGuestList = new GuestList();
        readGuestList.readFromFile();

        readGuestList.searchById(nationalIdNumber).showInformation();
        System.out.println("Room ID: " + roomId);
        System.out.println("Rental Days: " + rentalDays);
        System.out.println("Start Date: " + startDate);
        System.out.println("End Date: " + endDate);
        System.out.println("Name of Co-Tenant: " + nameOfCoTenant);
    }
}
