package javaapplication2.storage;

import javaapplication2.interf.I_List;
import javaapplication2.model.Room;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class RoomList extends ArrayList<Room> implements I_List<Room> {
    private String pathFile = "Active_Room_List.txt";

    @Override
    public boolean readFromFile() throws IOException {
        boolean check = false;
        File f = new File(pathFile);
        //check if file exists
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

        FileReader fr = new FileReader(pathFile);
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();
        this.clear();
        while (line != null) {
            try {
                String[] data = line.split("[;]");
                String roomId = data[0].trim();
                String roomName = data[1].trim();
                String roomType = data[2].trim();
                double dailyRate = Double.parseDouble(data[3].trim());
                int capacity = Integer.parseInt(data[4].trim());
                String funiture = data[5].trim();
                if (searchById(roomId) != null || capacity <= 0) {
                    line = br.readLine();
                    continue;
                }
                Room room = new Room(roomId, roomName, roomType, dailyRate, capacity, funiture);
                this.add(room);
                check = true;
            } catch (Exception e) {
                line = br.readLine();
                continue;
            }
            line = br.readLine();
        }
        br.close();
        fr.close();
        return check;
    }

    @Override
    public boolean saveToFile() {
        return false;
    }

    @Override
    public boolean addNew(Room room) {
        return false;
    }

    @Override
    public boolean update(Room room) {
        return false;
    }

    @Override
    public Room searchById(String id) {
        Room r = null;
        try {
            r = this.get(this.indexOf(new Room(id)));
        } catch (Exception e) {

        }
        return r;
    }



    @Override
    public void showAll() {
        if (this.isEmpty()) {
            System.out.println("No room available!");
        } else {
            for (Room room : this) {
                room.showInformation();
            }
        }
    }
}
