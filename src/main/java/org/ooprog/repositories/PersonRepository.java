package org.ooprog.repositories;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.ooprog.models.Contact;
import org.ooprog.models.Name;
import org.ooprog.models.Person;

import java.time.LocalDate;
import java.util.*;

/**
 * Implementation of the {@link IPersonRepository} interface
 *
 * @author Bartosz Drozd
 */

@SuppressWarnings("unchecked")
public class PersonRepository implements IPersonRepository {
    /**
     * An open session with the database
     */
    private final Session db;
    /**
     * A list of all persons in the application
     */
    private final ObservableList<Person> allPersons = FXCollections.observableArrayList();

    public PersonRepository(Session db) {
        this.db = db;
    }

    /**
     * Persists a {@link Person} to the database
     *
     * @param p The {@link Person} to be persisted to the database
     */
    @Override
    public void createPerson(Person p) {
        db.beginTransaction();
        db.save(p);
        // Refresh the list of persons to keep it in sync with DB
        refreshPersonsList();
        db.getTransaction().commit();
    }

    /**
     * Deletes a {@link Person} from the database
     *
     * @param p The {@link Person} to be deleted from the database
     */
    @Override
    public void deletePerson(Person p) {
        db.beginTransaction();
        db.delete(p);
        // Refresh the list of persons to keep it in sync with DB
        refreshPersonsList();
        db.getTransaction().commit();
    }

    /**
     * Finds persons based on a search string
     * This method searches for a matching ID or a matching {@link Name}
     *
     * @param searchString Name or ID to search the database for
     * @return A {@link List} of {@link Person} found for the provided searchString
     */
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

    /**
     * Refreshes the in-memory list of {@link Person} to synchronize with the database
     */
    private void refreshPersonsList() {
        allPersons.setAll(db.createQuery("from Person").getResultList());
    }

    /**
     * Fetches all persons in the database
     *
     * @return The in-memory {@link ObservableList} of {@link Person}
     */
    @Override
    public ObservableList<Person> getAllPersons() {
        refreshPersonsList();
        return FXCollections.unmodifiableObservableList(allPersons);
    }

    /**
     * @param id The ID of the {@link Person} to get close contacts for
     * @return A {@link List} of {@link Contact} for a person with the provided ID
     */
    @Override
    public List<Contact> getCloseContacts(int id) {
        String query = "select ct from Contact ct join Person p1 on (ct.person1.ID = p1.ID) join Person p2 on (ct.person2.ID = p2.ID) where ct.id.personid1 = :id or ct.id.personid2 = :id";
        var contacts = new ArrayList<Contact>();
        var res = db.createQuery(query).setParameter("id", id).getResultList();
        for (Object c : res) {
            contacts.add((Contact) c);
        }
        return contacts;
    }

    /**
     * Adds a close contact between two {@link Person}
     *
     * @param contact An object {@link Contact} holding two instances of {@link Person} and a date of the contact of type {@link LocalDate}
     */
    @Override
    public void addCloseContact(Contact contact) {
        db.beginTransaction();
        // Clear the session to prevent duplicate IDs
        db.clear();
        db.save(contact);
        db.getTransaction().commit();
    }
}
