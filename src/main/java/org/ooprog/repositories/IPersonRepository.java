package org.ooprog.repositories;

import javafx.collections.ObservableList;
import org.ooprog.models.Contact;
import org.ooprog.models.Person;

import java.util.List;

public interface IPersonRepository {
    void createPerson(Person p);

    void deletePerson(Person p);

    List<Person> findPerson(String searchString);

    ObservableList<Person> getAllPersons();

    List<Contact> getCloseContacts(int id);

    void addCloseContact(Contact contact);
}
