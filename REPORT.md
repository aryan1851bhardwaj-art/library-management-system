# LIBRARY MANAGEMENT SYSTEM

**A Project Report submitted for the Object-Oriented Programming (OOPS) Course Project (20 Marks)**

| | |
|---|---|
| **Submitted by** | _[Your Name]_ |
| **Roll Number** | _[Your Roll Number]_ |
| **Class / Section** | _[Your Class and Section]_ |
| **University** | _[Your University]_ |
| **Subject** | Object-Oriented Programming using Java |

---

## 1. Abstract

This project is a console-based Library Management System developed in Java.
It computerises the everyday work of a small library: adding books,
registering members, issuing and returning books, calculating late fines,
and searching the collection. All records are stored permanently using
Object Serialization, so data survives closing the program. The project is
designed to be small and easy to understand, while still demonstrating the
main pillars of Object-Oriented Programming — encapsulation, inheritance,
polymorphism and abstraction — together with custom exception handling and
file I/O.

## 2. Introduction

Libraries maintain records of books and members manually, which is slow and
error-prone. This system solves that problem with a simple menu-driven
program. A librarian can add books and members, issue a book to a member,
accept a return (with an automatic fine if the book is late), and search or
list the whole collection in seconds.

## 3. Objectives

1. To apply object-oriented design (classes, inheritance, polymorphism) to a real-world problem.
2. To handle invalid situations gracefully using custom exceptions.
3. To store application data permanently using File I/O and Object Serialization.
4. To implement searching and sorting of records.
5. To keep the system simple, modular (package-wise) and easy to explain.

## 4. System Design

### 4.1 Class diagram (text form)

```
                 Person (abstract)
                     ▲
                     │ extends
                  Member ──────────────┐
                                       │ borrows
   Book ◄────────── issued to ─────────┘

   Library  ── contains ──►  List<Book>, List<Member>
   LibraryApp ── uses ──►  Library, FileManager
   FileManager ── saves/loads ──►  Library (serialization)
```

### 4.2 Classes and their responsibilities

| Class | Package | Responsibility |
|---|---|---|
| `Book` | model | One book: id, title, author, issue status and date |
| `Person` | model | Abstract base class: id and name of a person |
| `Member` | model | A library member (extends `Person`); tracks borrowed books |
| `BookNotFoundException` | exception | Raised when a book id does not exist |
| `BookNotAvailableException` | exception | Raised when a book is already issued |
| `MemberNotFoundException` | exception | Raised when a member id is not registered |
| `Library` | service | All business logic: add, issue, return, fine, search, sort |
| `FileManager` | util | Saves/loads the `Library` object using serialization |
| `LibraryApp` | main | Menu-driven user interface (main class) |

### 4.3 Rules of the system

- A member can hold at most **3 books** at a time.
- A book can be kept for **14 days** free of charge.
- After 14 days, a fine of **Rs. 5 per extra day** is charged.
- A book that is already issued cannot be issued again until it is returned.

## 5. Implementation

The program is menu-driven. On start, `FileManager.load()` reads the saved
`Library` object from `library_data.ser`; if the file does not exist (first
run), sample data is loaded. Every menu action calls a method of `Library`,
which throws a custom exception when a rule is broken; `LibraryApp` catches
it and prints a friendly message instead of crashing. On exit,
`FileManager.save()` writes the object back using `ObjectOutputStream`.

Key concept usage:

- **Encapsulation** — fields of `Book` are private; state changes only through methods like `issueTo()` and `markReturned()`.
- **Inheritance & abstraction** — `Member` extends the abstract class `Person`.
- **Polymorphism** — `getRole()` is declared in `Person` and implemented in `Member`; `toString()` is overridden in model classes.
- **Custom exceptions** — three checked exceptions model real failure cases (book missing, book unavailable, member missing).
- **Serialization** — `Book`, `Member` and `Library` implement `Serializable`; one `writeObject()` call saves the entire system state.
- **Searching & sorting** — linear search by id/title/author; `Comparator` sorts books by title.

## 6. Output (sample)

```
==============================================
        LIBRARY MANAGEMENT SYSTEM
==============================================

---------------- MENU ----------------
1. Add a new book
2. Register a new member
3. Issue a book
4. Return a book
5. Search books by title or author
6. Display all books (sorted by title)
7. Display all members
0. Save and Exit
--------------------------------------
Enter your choice: 6
ID | Title | Author | Status
101 | Clean Code | Robert C. Martin | Available
104 | Data Structures in C | Reema Thareja | Available
102 | Head First Java | Kathy Sierra | Available
103 | The Alchemist | Paulo Coelho | Available
```

## 7. Testing

| # | Test case | Input | Expected result | Actual result |
|---|---|---|---|---|
| 1 | Display books | Choice 6 | All books listed, sorted by title | Passed |
| 2 | Issue available book | Book 102 → Member 1 | "Book issued successfully" | Passed |
| 3 | Issue same book again | Book 102 → Member 2 | Error: already issued (BookNotAvailableException) | Passed |
| 4 | Issue with wrong book id | Book 999 | Error: book not found (BookNotFoundException) | Passed |
| 5 | Issue with wrong member id | Member 99 | Error: member not registered (MemberNotFoundException) | Passed |
| 6 | Borrow limit | Issue a 4th book to Member 1 | Error: already holds 3 books | Passed |
| 7 | Return book | Book 102 | "Book returned successfully. No fine." | Passed |
| 8 | Search | "java" | Head First Java is listed | Passed |
| 9 | Persistence | Exit, run again | Previously added/issued data still present | Passed |
| 10 | Invalid menu input | "abc" as book id | "Please enter a valid number." and re-prompt | Passed |

## 8. Conclusion

The Library Management System meets all its objectives in a compact,
easy-to-explain codebase. It demonstrates the four pillars of OOP, custom
exception handling, and permanent storage through serialization. The
modular package structure makes every class's job clear, which makes the
project easy to present and extend.

## 9. Future Scope

- A JDBC/database version (Unit 3) instead of file storage.
- A graphical interface using Java Swing.
- A Spring Boot web version with a REST API.
- Fine payment records and per-member borrowing history reports.

## 10. References

1. Kathy Sierra & Bert Bates, *Head First Java*, O'Reilly.
2. Oracle Java Documentation — https://docs.oracle.com/javase/tutorial/
3. Course syllabus material: Units 1–3 (OOP, I/O & Multithreading, GUI & JDBC).
