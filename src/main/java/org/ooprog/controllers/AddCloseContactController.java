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
            // Add checks
            Person p1 = view.getLeftList().getSelectionModel().getSelectedItem();
            Person p2 = view.getRightList().getSelectionModel().getSelectedItem();

            var closeContact = new Contact(p1, p2, LocalDate.now());
            var id = new ContactCompositeID(p1.getID(), p2.getID());
            closeContact.setId(id);

            p1.getCloseContacts().add(closeContact);
            repo.addCloseContact(closeContact);
        });
    }

    public Parent getView() {
        return view.getRoot();
    }
}
