package org.ooprog.repositories;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.ooprog.models.Contact;
import org.ooprog.models.Person;

import java.util.*;

@SuppressWarnings("unchecked")
public class PersonRepository implements IPersonRepository {
    private final Session db;
    private final ObservableList<Person> allPersons = FXCollections.observableArrayList();

    public PersonRepository(Session db) {
        this.db = db;
    }

    @Override
    public void createPerson(Person p) {
        db.beginTransaction();
        db.save(p);
        // Refresh the list of persons to keep it in sync with DB
        refreshPersonsList();
        db.getTransaction().commit();
    }

    @Override
    public void deletePerson(Person p) {
        db.beginTransaction();
        db.delete(p);
        // Refresh the list of persons to keep it in sync with DB
        refreshPersonsList();
        db.getTransaction().commit();
    }

    @Override
    public List<Person> findPerson(String searchString) {
        String query = "from Person p WHERE concat(p.name.firstName, ' ', p.name.middleName, ' ', p.name.lastName) LIKE :searchString or cast(p.ID as string) = :searchID";

        // Handle any combination of first, middle and last name
        var words = searchString.split(" ");
        var searchParam = String.join("%", words);

        return db.createQuery(query)
                .setParameter("searchString", "%" + searchParam + "%")
                // Handle searching with ID
                .setParameter("searchID", searchParam)
                .getResultList();
    }

    private void refreshPersonsList() {
        allPersons.setAll(db.createQuery("from Person").getResultList());
    }

    @Override
    public ObservableList<Person> getAllPersons() {
        refreshPersonsList();
        return FXCollections.unmodifiableObservableList(allPersons);
    }

    @Override
    public List<Contact> getCloseContacts(int id) {
        String query = "select ct from Contact ct join Person p1 on (ct.person1.ID = p1.ID) join Person p2 on (ct.person2.ID = p2.ID) where ct.id.personid1 = :id or ct.id.personid2 = :id";
        var test = new ArrayList<Contact>();
        var res = db.createQuery(query).setParameter("id", id).getResultList();
        for (Object c : res) {
            test.add((Contact) c);
        }
        return test;
    }

    @Override
    public void addCloseContact(Contact contact) {
        db.beginTransaction();
        db.save(contact);
        db.getTransaction().commit();
    }
}
