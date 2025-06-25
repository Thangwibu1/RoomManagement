package javaapplication2.model;

import java.io.Serializable;

public class Guest implements Serializable {
    private String nationalIdNumber;
    private String fullName;
    private String birthDate;
    private String gender;
    private String phoneNumber;
//    private String roomId;
//    private int rentalDays;
//    private String startDate;
//    private String endDate;
//    private String nameOfCoTenant;
    private static final long serialVersionUID = 101459454562460255L;

    // Default constructor
    public Guest() {
    }
    // Constructor with parameters


    public Guest(String nationalIdNumber, String fullName, String birthDate, String gender, String phoneNumber) {
        this.nationalIdNumber = nationalIdNumber;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
    }

    //Constructor with nationalIdNumber
    public Guest(String nationalIdNumber) {
        this.nationalIdNumber = nationalIdNumber;
    }

    // Getters and Setters
    

    public String getNationalIdNumber() {
        return nationalIdNumber;
    }

    public void setNationalIdNumber(String nationalIdNumber) {
        this.nationalIdNumber = nationalIdNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    

    @Override
    public String toString() {
        return "Guest{" +
                "nationalIdNumber='" + nationalIdNumber + '\'' +
                ", fullName='" + fullName + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", gender='" + gender + '\'' +
                ", phoneNumber='" + phoneNumber + '\''+
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        Guest guest = (Guest) obj;
        return this.nationalIdNumber.equals(guest.getNationalIdNumber());
    }

    //showInformation method
    public void showInformation() {
        System.out.printf("%-15s | %-20s | %-12s | %-8s | %-12s%n",
                nationalIdNumber, fullName, birthDate, gender, phoneNumber);
    }
}
