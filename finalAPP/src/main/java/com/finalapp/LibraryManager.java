package com.finalapp;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages the book collection and handles data persistence
 */
public class LibraryManager {
    private List<Book> books;
    private static final String SAVE_FILE = "library.dat";

    public LibraryManager() {
        books = new ArrayList<>();
        loadBooks();
    }

    /**
     * Adds a new book to the library
     * @param book Book to add
     * @return true if successful
     */
    public boolean addBook(Book book) {
        if (book != null) {
            books.add(book);
            saveBooks();
            return true;
        }
        return false;
    }

    /**
     * Saves books to file
     */
    private void saveBooks() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(SAVE_FILE))) {
            oos.writeObject(books);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads books from file
     */
    @SuppressWarnings("unchecked")
    private void loadBooks() {
        if (new File(SAVE_FILE).exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream(SAVE_FILE))) {
                books = (List<Book>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
} 