package com.library.exception;

/**
 * Custom checked exception: thrown when a book exists but is already issued
 * to another member, so it cannot be issued again.
 */
public class BookNotAvailableException extends Exception {

    public BookNotAvailableException(int bookId) {
        super("Book with ID " + bookId + " is already issued to another member.");
    }
}
