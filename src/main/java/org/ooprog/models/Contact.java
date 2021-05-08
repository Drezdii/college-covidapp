package org.ooprog.models;

import org.hibernate.annotations.CollectionType;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity(name = "Contact")
@Table(name = "contact_table")
public class Contact {
    @EmbeddedId
    private ContactCompositeID id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "personid1", insertable = false, updatable = false)
    private Person person1;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "personid2", insertable = false, updatable = false)
    private Person person2;

    @Column(name = "dateContact")
    private final LocalDate dateContact = LocalDate.now();

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        var that = (Contact) obj;
        return Objects.equals(person1, that.person1) && Objects.equals(person2, that.person2);
    }

    public ContactCompositeID getId() {
        return id;
    }

    public Person getPerson1() {
        return person1;
    }

    public Person getPerson2() {
        return person2;
    }

    public LocalDate getDateContact() {
        return dateContact;
    }
}
