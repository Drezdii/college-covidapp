package org.ooprog.controllers;

import javafx.scene.Parent;
import org.ooprog.models.Contact;
import org.ooprog.models.ContactCompositeID;
import org.ooprog.models.Person;
import org.ooprog.repositories.IPersonRepository;
import org.ooprog.views.AddCloseContactView;

import java.time.LocalDate;

public class AddCloseContactController {
    private final AddCloseContactView view;
    private final IPersonRepository repo;

    public AddCloseContactController(AddCloseContactView view, IPersonRepository repository) {
        this.view = view;
        repo = repository;
        view.getLeftList().setItems(repo.getAllPersons());
        view.getRightList().setItems(repo.getAllPersons());
        setListeners();
    }

    private void setListeners() {
        view.getSaveButton().setOnAction(e -> {
            Person p1 = view.getLeftList().getSelectionModel().getSelectedItem();
            Person p2 = view.getRightList().getSelectionModel().getSelectedItem();
            LocalDate contactDate = view.getDatePicker().getValue();
            addCloseContact(p1, p2, contactDate);
        });
    }

    public Contact addCloseContact(Person p1, Person p2, LocalDate date) {
        if (p1 == null || p2 == null) {
            return null;
        }
        if (p1.getID() == p2.getID()) {
            return null;
        }
        System.out.println(date);
        var closeContact = new Contact(p1, p2, date);
        var id = new ContactCompositeID(p1.getID(), p2.getID());
        closeContact.setId(id);

        p1.getCloseContacts().add(closeContact);
        repo.addCloseContact(closeContact);
        return closeContact;
    }

    public Parent getView() {
        return view.getRoot();
    }
}
