package com.library.model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Represents one book in the library.
 *
 * Concepts used:
 *  - Encapsulation: all fields are private, access is through getters/setters.
 *  - Serializable: Book objects are saved to a file using Object Serialization.
 */
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    private int bookId;
    private String title;
    private String author;
    private boolean issued;          // true if the book is currently issued
    private int issuedToMemberId;    // member id who has the book, -1 if not issued
    private LocalDate issueDate;     // date on which the book was issued

    public Book(int bookId, String title, String author) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.issued = false;
        this.issuedToMemberId = -1;
        this.issueDate = null;
    }

    public int getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isIssued() {
        return issued;
    }

    public int getIssuedToMemberId() {
        return issuedToMemberId;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    /** Marks the book as issued to a member on a given date. */
    public void issueTo(int memberId, LocalDate date) {
        this.issued = true;
        this.issuedToMemberId = memberId;
        this.issueDate = date;
    }

    /** Marks the book as returned and available again. */
    public void markReturned() {
        this.issued = false;
        this.issuedToMemberId = -1;
        this.issueDate = null;
    }

    @Override
    public String toString() {
        String status = issued ? "Issued to member " + issuedToMemberId + " on " + issueDate : "Available";
        return bookId + " | " + title + " | " + author + " | " + status;
    }
}
