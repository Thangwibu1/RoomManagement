package javaapplication2.storage;

import javaapplication2.interf.I_List;
import javaapplication2.model.Guest;
import javaapplication2.model.Reservation;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ReservationList extends ArrayList<Reservation> implements I_List<Reservation> {
    private String pathFile = "Reservation_List.dat";

    
    @Override
    public boolean readFromFile() throws IOException {
        boolean check = false;
        File f = new File(pathFile);
        // Check if file exists
        if(!f.exists()) {
            try {
                f.createNewFile();
                System.out.println("File created successfully: " + pathFile);
                check = true;
            } catch (Exception e) {
                System.out.println("Error creating file: " + e.getMessage());
                check = false;
            }
        }
        if(f.length() == 0) {
            check = true;
        } else {
            try(FileInputStream is = new FileInputStream(pathFile);
                ObjectInputStream of = new ObjectInputStream(is)) {
                this.clear();
                try {
                    try {
                        while (true) {
                            Reservation reserved = (Reservation) of.readObject();

                            this.add(reserved);
                            check = true; // Successfully read reservations
                        }
                    } catch (EOFException e) {
                        // End of file reached, no action needed
                        System.out.println(this.size());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    // End of file reached, no action needed
                } finally {
                    of.close();
                    is.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                // End of file reached, no action needed
            }
            check = true;
        }
        return false;
    }

    @Override
    public boolean saveToFile() {
        boolean check = false;
        try (FileOutputStream fileOut = new FileOutputStream(pathFile);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {

            // Giả sử class hiện tại implement Iterable<Customer> hoặc extend Collection<Customer>
            for (Reservation reversed : this) {
                if (reversed == null) {
                    continue;
                }
                objectOut.writeObject(reversed);
            }
            objectOut.flush(); // Đảm bảo dữ liệu được ghi xuống
            check = true;

        } catch (NotSerializableException e) {
            System.err.println("Lỗi: Customer class hoặc một trong các thuộc tính của nó không implement Serializable");
            System.err.println("Class gây lỗi: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Lỗi IO khi lưu: " + e.getMessage());
            e.printStackTrace();
        }

        return check;
    }

    @Override
    public boolean addNew(Reservation reservation) {
        boolean check = false;
        if (reservation == null) {
            check = false;
        } else {
            this.add(reservation);
            check = true;
        }
        return check;
    }

    @Override
    public boolean update(Reservation reservation) {
        boolean check = false;
        for (Reservation r : this) {
            if (reservation.getReservedId().equals(r.getReservedId())) {
                r.setRentalDays(reservation.getRentalDays());
                r.setEndDate(reservation.getEndDate());
                r.setNameOfCoTenant(reservation.getNameOfCoTenant());
            }
        }
        return check;
    }

    @Override
    public Reservation searchById(String id) {
        return null;
    }

    @Override
    public void showAll() throws IOException{
        for (Reservation reservation : this) {
            reservation.showInformation();
        }
    }

    public boolean isAvailable(String roomId, String startDate, String endDate) {
        ArrayList<Reservation> reservations = searchByRoomId(roomId);
        System.out.println("DEBUG" + reservations.size());
        boolean check = true;
        for (Reservation reservation : reservations) {
            LocalDate startDateLocal = LocalDate.parse(startDate.substring(6) + "-" + startDate.substring(3, 5) + "-" + startDate.substring(0, 2));
            LocalDate endDateLocal = LocalDate.parse(endDate.substring(6) + "-" + endDate.substring(3, 5) + "-" + endDate.substring(0, 2));

            LocalDate reservedStart = LocalDate.parse(reservation.getStartDate().substring(6) + "-" + reservation.getStartDate().substring(3, 5) + "-" + reservation.getStartDate().substring(0, 2));
            LocalDate reservedEnd = LocalDate.parse(reservation.getEndDate().substring(6) + "-" + reservation.getEndDate().substring(3, 5) + "-" + reservation.getEndDate().substring(0, 2));

            // Check if the new reservation overlaps with an existing one
            if (startDateLocal.isBefore(reservedEnd) || endDateLocal.isAfter(reservedStart) || startDateLocal.isEqual(reservedEnd) || startDateLocal.isEqual(reservedStart)) {
                check = false;
            }
        }
        return check; // Room is available
    }

    public ArrayList<Reservation> searchByRoomId(String roomId) {
        ArrayList<Reservation> reservations = new ArrayList<>();
        for (Reservation reservation : this) {
            if (reservation.getRoomId().equals(roomId)) {
                reservations.add(reservation);
            }
        }
        return reservations; // Return all reservations for the specified room ID
    }

    public ArrayList<Reservation> searchByGuestId(String guestId) {
    ArrayList<Reservation> reservations = new ArrayList<>();
        for (Reservation reservation : this) {
            if (reservation.getNationalIdNumber().equals(guestId)) {
                reservations.add(reservation);
            }
        }
        return reservations; // Return all reservations for the specified guest ID
    }
}
