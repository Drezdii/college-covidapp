import javafx.collections.ObservableList;
import org.ooprog.models.Contact;
import org.ooprog.models.Person;
import org.ooprog.repositories.IPersonRepository;

import java.util.List;

public class TestingRepostiory implements IPersonRepository {
    @Override
    public void createPerson(Person p) {

    }

    @Override
    public void deletePerson(Person p) {

    }

    @Override
    public List<Person> findPerson(String searchString) {
        return null;
    }

    @Override
    public ObservableList<Person> getAllPersons() {
        return null;
    }

    @Override
    public List<Contact> getCloseContacts(int id) {
        return null;
    }

    @Override
    public void addCloseContact(Contact contact) {

    }
}
