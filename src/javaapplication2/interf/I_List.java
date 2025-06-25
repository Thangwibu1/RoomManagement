package javaapplication2.interf;

import java.io.IOException;

public interface I_List<T> {
    //Read data from file
    public boolean readFromFile() throws IOException;

    //Save data to file
    public boolean saveToFile();

    //Add new item
    public boolean addNew(T t);

    //Update item
    public boolean update(T t);

    //Search item by ID
    public T searchById(String id);

    //Show all items
    public void showAll() throws IOException;

}
