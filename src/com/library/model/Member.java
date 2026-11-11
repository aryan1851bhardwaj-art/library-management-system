package com.library.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A library member. Members borrow and return books.
 *
 * Concepts used:
 *  - Inheritance: Member extends the abstract Person class.
 *  - Polymorphism: getRole() is implemented here with member-specific text.
 */
public class Member extends Person {

    private static final long serialVersionUID = 1L;

    /** Maximum number of books a member can hold at one time. */
    public static final int MAX_BOOKS_ALLOWED = 3;

    private final List<Integer> borrowedBookIds = new ArrayList<>();

    public Member(int id, String name) {
        super(id, name);
    }

    @Override
    public String getRole() {
        return "Library Member";
    }

    public List<Integer> getBorrowedBookIds() {
        return borrowedBookIds;
    }

    public boolean canBorrowMore() {
        return borrowedBookIds.size() < MAX_BOOKS_ALLOWED;
    }

    public void borrowBook(int bookId) {
        borrowedBookIds.add(bookId);
    }

    public void returnBook(int bookId) {
        borrowedBookIds.remove(Integer.valueOf(bookId));
    }

    @Override
    public String toString() {
        return id + " | " + name + " | Books borrowed: " + borrowedBookIds.size()
                + " " + borrowedBookIds;
    }
}
