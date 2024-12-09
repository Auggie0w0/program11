package com.finalapp;

/**
 * Represents a book in the library
 * Contains book details and reading progress
 */
public class Book {
    private String title;
    private String author;
    private int pages;
    private int pagesRead;
    private String notes;

    public Book(String title, String author, int pages) {
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.pagesRead = 0;
        this.notes = "";
    }

    // Getters and setters
    // ... (add standard getters/setters for all fields)

    /**
     * Updates reading progress
     * @param pagesRead number of pages read
     * @return true if valid update, false otherwise
     */
    public boolean updateProgress(int pagesRead) {
        if (pagesRead >= 0 && pagesRead <= pages) {
            this.pagesRead = pagesRead;
            return true;
        }
        return false;
    }

    /**
     * Calculates reading progress as percentage
     * @return progress percentage
     */
    public double getProgressPercentage() {
        return (double) pagesRead / pages * 100;
    }
} 