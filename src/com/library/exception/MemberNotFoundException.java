package com.library.exception;

/**
 * Custom checked exception: thrown when a member id is not registered.
 */
public class MemberNotFoundException extends Exception {

    public MemberNotFoundException(int memberId) {
        super("Member with ID " + memberId + " is not registered in the library.");
    }
}
