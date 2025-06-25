package javaapplication2.storage;

import javaapplication2.interf.I_List;
import javaapplication2.model.Guest;

import java.io.*;
import java.util.ArrayList;

public class GuestList extends ArrayList<Guest> implements I_List<Guest> {
    String pathFile = "Guest_List.dat";

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
            FileInputStream fis = new FileInputStream(pathFile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            this.clear();
            try {
                while (true) {
                    try {
                        Guest g = (Guest) ois.readObject();
                        this.addNew(g);
                        check = true;// Successfully read guest
                    } catch (EOFException e) {
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                ois.close();
                fis.close();
            }

        }


        return check;
    }

    @Override
    public boolean saveToFile() {
        boolean check = false;
        try (FileOutputStream fileOut = new FileOutputStream(pathFile);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {

            // Giả sử class hiện tại implement Iterable<Customer> hoặc extend Collection<Customer>
            for (Guest guest : this) {
                if (guest == null) {
                    continue;
                }
                objectOut.writeObject(guest);
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
    public boolean addNew(Guest guest) {
        boolean check = false;
        if (guest == null) {
            System.out.println("Guest cannot be null.");
            check = false;
        } else {
            this.add(guest);
            check = true;
        }
        return check;
    }

    @Override
    public boolean update(Guest guest) {
        boolean check = false;
        if (guest == null) {
            System.out.println("Guest cannot be null.");
            check = false;
        } else {
            Guest existingGuest = this.searchById(guest.getNationalIdNumber());
            if (existingGuest != null) {
                existingGuest.setPhoneNumber(guest.getPhoneNumber());

                check = true;

            }
        }
        return check;
    }

    @Override
    public Guest searchById(String id) {
        if (this.indexOf(new Guest(id)) >= 0) {
            return this.get(this.indexOf(new Guest(id)));
        } else {
            return null;
        }
    }

    @Override
    public void showAll() {
        if (this.isEmpty()) {
            System.out.println("Empty list of guests.");
        } else {
            System.out.println("-----------------------------------------------------------------");
            System.out.println("List of Guests");
            System.out.println("-----------------------------------------------------------------");
            for (Guest guest : this) {
                guest.showInformation();
            }
        }
    }
}
