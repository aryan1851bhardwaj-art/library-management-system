package com.library.service;

import com.library.exception.BookNotAvailableException;
import com.library.exception.BookNotFoundException;
import com.library.exception.MemberNotFoundException;
import com.library.model.Book;
import com.library.model.Member;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * The core of the system. Holds all books and members and performs
 * every operation: add, issue, return, search and sort.
 *
 * The whole Library object (with all books and members inside it) is
 * saved to a file using Object Serialization, so no data is lost
 * when the program closes.
 */
public class Library implements Serializable {

    private static final long serialVersionUID = 1L;

    /** A book can be kept for this many days without any fine. */
    public static final int FREE_DAYS = 14;
    /** Fine charged per extra day, in rupees. */
    public static final int FINE_PER_DAY = 5;

    private final List<Book> books = new ArrayList<>();
    private final List<Member> members = new ArrayList<>();

    // ---------------- Add operations ----------------

    public void addBook(Book book) {
        books.add(book);
    }

    public void addMember(Member member) {
        members.add(member);
    }

    public List<Book> getBooks() {
        return books;
    }

    public List<Member> getMembers() {
        return members;
    }

    // ---------------- Find operations (linear search) ----------------

    public Book findBook(int bookId) throws BookNotFoundException {
        for (Book book : books) {
            if (book.getBookId() == bookId) {
                return book;
            }
        }
        throw new BookNotFoundException(bookId);
    }

    public Member findMember(int memberId) throws MemberNotFoundException {
        for (Member member : members) {
            if (member.getId() == memberId) {
                return member;
            }
        }
        throw new MemberNotFoundException(memberId);
    }

    /** Searches books whose title or author contains the given text. */
    public List<Book> searchBooks(String text) {
        List<Book> result = new ArrayList<>();
        String key = text.toLowerCase();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(key)
                    || book.getAuthor().toLowerCase().contains(key)) {
                result.add(book);
            }
        }
        return result;
    }

    /** Returns all books sorted alphabetically by title. */
    public List<Book> booksSortedByTitle() {
        List<Book> sorted = new ArrayList<>(books);
        sorted.sort(Comparator.comparing(Book::getTitle));
        return sorted;
    }

    // ---------------- Issue and return ----------------

    /**
     * Issues a book to a member.
     *
     * @throws BookNotFoundException     if the book id does not exist
     * @throws MemberNotFoundException   if the member id does not exist
     * @throws BookNotAvailableException if the book is already issued
     * @throws IllegalStateException     if the member already holds the maximum books
     */
    public void issueBook(int bookId, int memberId)
            throws BookNotFoundException, MemberNotFoundException, BookNotAvailableException {
        Book book = findBook(bookId);
        Member member = findMember(memberId);

        if (book.isIssued()) {
            throw new BookNotAvailableException(bookId);
        }
        if (!member.canBorrowMore()) {
            throw new IllegalStateException(
                    "Member " + memberId + " already holds " + Member.MAX_BOOKS_ALLOWED + " books.");
        }

        book.issueTo(memberId, LocalDate.now());
        member.borrowBook(bookId);
    }

    /**
     * Returns a book and calculates the fine.
     *
     * @return the fine amount in rupees (0 if returned within FREE_DAYS)
     */
    public int returnBook(int bookId) throws BookNotFoundException, MemberNotFoundException {
        Book book = findBook(bookId);
        if (!book.isIssued()) {
            throw new IllegalStateException("Book " + bookId + " is not issued to anyone.");
        }

        long daysKept = ChronoUnit.DAYS.between(book.getIssueDate(), LocalDate.now());
        long extraDays = daysKept - FREE_DAYS;
        int fine = extraDays > 0 ? (int) extraDays * FINE_PER_DAY : 0;

        Member member = findMember(book.getIssuedToMemberId());
        member.returnBook(bookId);
        book.markReturned();
        return fine;
    }
}
