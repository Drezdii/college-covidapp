package org.ooprog.repositories;

import org.ooprog.models.Contact;
import org.ooprog.models.Person;

import java.util.List;

public interface IPersonRepository {
    void createPerson(Person p);

    void deletePerson(Person p);

    List<Person> findPerson(String searchString);

    List<Person> getAllPersons();

    List<Contact> getCloseContacts(int id);
}
