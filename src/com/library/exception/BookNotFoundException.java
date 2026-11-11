package com.library.exception;

/**
 * Custom checked exception: thrown when a book id does not exist in the library.
 */
public class BookNotFoundException extends Exception {

    public BookNotFoundException(int bookId) {
        super("Book with ID " + bookId + " was not found in the library.");
    }
}
