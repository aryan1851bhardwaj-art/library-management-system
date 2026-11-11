package com.library.util;

import com.library.service.Library;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Saves and loads the whole Library object using Object Serialization.
 *
 * Concepts used:
 *  - File I/O with byte streams (FileInputStream / FileOutputStream)
 *  - Object Serialization / Deserialization (ObjectOutputStream / ObjectInputStream)
 *  - Exception handling for file errors (file missing, corrupted data, etc.)
 */
public class FileManager {

    private static final String DATA_FILE = "library_data.ser";

    /** Saves the library to the data file. */
    public static void save(Library library) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            out.writeObject(library);
        } catch (IOException e) {
            System.out.println("Warning: could not save data. " + e.getMessage());
        }
    }

    /**
     * Loads the library from the data file.
     * If the file does not exist yet (first run), a fresh empty library is returned.
     */
    public static Library load() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            return new Library();
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (Library) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Warning: saved data could not be read, starting fresh. " + e.getMessage());
            return new Library();
        }
    }
}
