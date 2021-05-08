package org.ooprog.models;

import net.bytebuddy.implementation.bind.annotation.IgnoreForBinding;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ContactCompositeID implements Serializable {
    @Column(name = "personid1")
    private int personid1;
    @Column(name = "personid2")
    private int personid2;

    public ContactCompositeID() {
    }

    public ContactCompositeID(int personid1, int personid2) {
        this.personid1 = personid1;
        this.personid2 = personid2;
    }

    public int getPersonid1() {
        return personid1;
    }

    public int getPersonid2() {
        return personid2;
    }

    @Override
    public int hashCode() {
        return Objects.hash(personid1, personid2);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        var that = (ContactCompositeID) obj;
        return Objects.equals(personid1, that.personid1) && Objects.equals(personid2, that.personid2);
    }
}
