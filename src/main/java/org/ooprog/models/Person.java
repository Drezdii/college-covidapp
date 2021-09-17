package org.ooprog.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "firstName", column = @Column(name = "firstName")),
            @AttributeOverride(name = "middleName", column = @Column(name = "middleName")),
            @AttributeOverride(name = "lastName", column = @Column(name = "lastName"))
    })
    private Name name;
    private String email;
    private String phoneNumber;
    @OneToMany(mappedBy = "person1")
    private final Set<Contact> closeContacts = new HashSet<>();

    public Person(int ID, Name name, String email, String phoneNumber) {
        this.ID = ID;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public Person() {

    }

    public int getID() {
        return ID;
    }

    public Name getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Set<Contact> getCloseContacts() {
        return closeContacts;
    }

    @Override
    public String toString() {
        return name.toString() + " #" + ID + "\nEmail: "
                + email + "\nPhone: " + phoneNumber;
    }
}
