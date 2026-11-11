package com.library.main;

import com.library.exception.BookNotAvailableException;
import com.library.exception.BookNotFoundException;
import com.library.exception.MemberNotFoundException;
import com.library.model.Book;
import com.library.model.Member;
import com.library.service.Library;
import com.library.util.FileManager;

import java.util.List;
import java.util.Scanner;

/**
 * Library Management System - main class with a simple menu.
 *
 * Flow of the program:
 *   1. Data is loaded from the file (serialization) when the program starts.
 *   2. The user picks options from the menu (add / issue / return / search ...).
 *   3. Data is saved back to the file when the program exits.
 */
public class LibraryApp {

    private static Library library;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        library = FileManager.load();
        if (library.getBooks().isEmpty()) {
            addSampleData();   // first run: a few books and members to try the system
        }

        System.out.println("==============================================");
        System.out.println("        LIBRARY MANAGEMENT SYSTEM");
        System.out.println("==============================================");

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Enter your choice: ");
            switch (choice) {
                case 1 -> addBook();
                case 2 -> addMember();
                case 3 -> issueBook();
                case 4 -> returnBook();
                case 5 -> searchBooks();
                case 6 -> displayAllBooks();
                case 7 -> displayAllMembers();
                case 0 -> {
                    FileManager.save(library);
                    System.out.println("Data saved. Thank you for using the Library Management System!");
                    running = false;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n---------------- MENU ----------------");
        System.out.println("1. Add a new book");
        System.out.println("2. Register a new member");
        System.out.println("3. Issue a book");
        System.out.println("4. Return a book");
        System.out.println("5. Search books by title or author");
        System.out.println("6. Display all books (sorted by title)");
        System.out.println("7. Display all members");
        System.out.println("0. Save and Exit");
        System.out.println("--------------------------------------");
    }

    // ---------------- Menu actions ----------------

    private static void addBook() {
        int id = readInt("Enter book ID: ");
        System.out.print("Enter book title: ");
        String title = scanner.nextLine().trim();
        System.out.print("Enter book author: ");
        String author = scanner.nextLine().trim();
        try {
            library.findBook(id);
            System.out.println("A book with ID " + id + " already exists. Book not added.");
        } catch (BookNotFoundException e) {
            library.addBook(new Book(id, title, author));
            System.out.println("Book added successfully.");
        }
    }

    private static void addMember() {
        int id = readInt("Enter member ID: ");
        System.out.print("Enter member name: ");
        String name = scanner.nextLine().trim();
        try {
            library.findMember(id);
            System.out.println("A member with ID " + id + " already exists. Member not added.");
        } catch (MemberNotFoundException e) {
            library.addMember(new Member(id, name));
            System.out.println("Member registered successfully.");
        }
    }

    private static void issueBook() {
        int bookId = readInt("Enter book ID to issue: ");
        int memberId = readInt("Enter member ID: ");
        try {
            library.issueBook(bookId, memberId);
            System.out.println("Book issued successfully. Please return within "
                    + Library.FREE_DAYS + " days to avoid a fine.");
        } catch (BookNotFoundException | MemberNotFoundException
                 | BookNotAvailableException | IllegalStateException e) {
            System.out.println("Cannot issue book: " + e.getMessage());
        }
    }

    private static void returnBook() {
        int bookId = readInt("Enter book ID to return: ");
        try {
            int fine = library.returnBook(bookId);
            if (fine > 0) {
                System.out.println("Book returned. Late fine: Rs. " + fine);
            } else {
                System.out.println("Book returned successfully. No fine.");
            }
        } catch (BookNotFoundException | MemberNotFoundException | IllegalStateException e) {
            System.out.println("Cannot return book: " + e.getMessage());
        }
    }

    private static void searchBooks() {
        System.out.print("Enter title or author to search: ");
        String text = scanner.nextLine().trim();
        List<Book> results = library.searchBooks(text);
        if (results.isEmpty()) {
            System.out.println("No books found for \"" + text + "\".");
        } else {
            results.forEach(System.out::println);
        }
    }

    private static void displayAllBooks() {
        List<Book> books = library.booksSortedByTitle();
        if (books.isEmpty()) {
            System.out.println("No books in the library yet.");
        } else {
            System.out.println("ID | Title | Author | Status");
            books.forEach(System.out::println);
        }
    }

    private static void displayAllMembers() {
        List<Member> members = library.getMembers();
        if (members.isEmpty()) {
            System.out.println("No members registered yet.");
        } else {
            System.out.println("ID | Name | Borrowed books");
            members.forEach(System.out::println);
        }
    }

    // ---------------- Helpers ----------------

    /** Reads an integer safely; keeps asking until a valid number is entered. */
    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    /** A few records so the system can be demonstrated immediately. */
    private static void addSampleData() {
        library.addBook(new Book(101, "Clean Code", "Robert C. Martin"));
        library.addBook(new Book(102, "Head First Java", "Kathy Sierra"));
        library.addBook(new Book(103, "The Alchemist", "Paulo Coelho"));
        library.addBook(new Book(104, "Data Structures in C", "Reema Thareja"));
        library.addMember(new Member(1, "Aarav Sharma"));
        library.addMember(new Member(2, "Priya Singh"));
    }
}
