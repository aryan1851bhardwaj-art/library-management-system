# Library Management System

A simple, console-based **Library Management System** written in Java.
It is built as an Object-Oriented Programming (OOPS) course project and
demonstrates the core Java syllabus concepts in one small, easy-to-explain
application.

## Features

- Add new books and register members
- Issue a book to a member (a member can hold at most 3 books)
- Return a book with an automatic late fine (Rs. 5/day after 14 free days)
- Search books by title or author
- Display all books sorted alphabetically by title
- Data is **saved automatically** and loaded again on the next run
  (Object Serialization — no data is lost when the program closes)

## Concepts demonstrated (syllabus mapping)

| Concept | Where it is used |
|---|---|
| Classes & encapsulation | `Book`, `Member` — private fields, getters |
| Inheritance & abstraction | abstract class `Person` → `Member` extends it |
| Polymorphism | `getRole()` implemented differently per class |
| Packages | `model`, `exception`, `service`, `util`, `main` |
| Custom exceptions | `BookNotFoundException`, `BookNotAvailableException`, `MemberNotFoundException` |
| Exception handling | try-catch around issue/return and file loading |
| File I/O & serialization | `FileManager` saves/loads the whole library object |
| Searching & sorting | linear search; books sorted by title with a `Comparator` |
| Collections | `ArrayList` for books and members |

## Project structure

```
src/com/library/
├── model/       Book.java, Person.java (abstract), Member.java
├── exception/   BookNotFoundException, BookNotAvailableException, MemberNotFoundException
├── service/     Library.java   (all business logic: add, issue, return, search, sort, fine)
├── util/        FileManager.java (serialization save/load)
└── main/        LibraryApp.java (menu-driven main class)
```

## How to compile and run

From the project root folder:

```bash
# compile
javac -d out $(find src -name "*.java")

# run
java -cp out com.library.main.LibraryApp
```

On Windows (Command Prompt), compile with:

```bat
javac -d out src\com\library\model\*.java src\com\library\exception\*.java src\com\library\service\*.java src\com\library\util\*.java src\com\library\main\*.java
java -cp out com.library.main.LibraryApp
```

**Requirements:** JDK 17 or above (uses switch expressions and `LocalDate`).

## Sample data

On the first run, the program starts with 4 books and 2 members so every
feature can be tried immediately. Data is stored in a file named
`library_data.ser` in the folder from which the program is run.

## Sample run

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
Enter your choice: 3
Enter book ID to issue: 102
Enter member ID: 1
Book issued successfully. Please return within 14 days to avoid a fine.
```

See **REPORT.md** for the full project report (design, class diagram in text,
test cases, and conclusion).
