package com.library.model;

import java.io.Serializable;

/**
 * Abstract base class for every person in the system.
 *
 * Concepts used:
 *  - Abstraction: Person cannot be created directly, only through subclasses.
 *  - Inheritance: Member extends Person and reuses its fields and methods.
 */
public abstract class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    protected int id;
    protected String name;

    public Person(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    /** Every type of person describes its role differently (polymorphism). */
    public abstract String getRole();
}
