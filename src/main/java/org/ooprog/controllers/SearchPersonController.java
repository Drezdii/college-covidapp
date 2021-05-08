package org.ooprog.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import org.ooprog.models.Contact;
import org.ooprog.models.Person;
import org.ooprog.repositories.IPersonRepository;
import org.ooprog.views.SearchPersonView;

public class SearchPersonController {
    private final SearchPersonView view;
    private final IPersonRepository repo;
    private final ObservableList<Person> persons = FXCollections.observableArrayList();

    public SearchPersonController(SearchPersonView view, IPersonRepository repository) {
        this.view = view;
        repo = repository;
        // Bind the ListView to the persons
        view.getPersonList().setItems(persons);
        setListeners();
    }

    private void setListeners() {
        view.getSearchButton().setOnAction(e -> {
            var searchString = view.getSearchField().getText();
            if (!searchString.isBlank()) {
                var result = repo.findPerson(searchString);
                persons.setAll(result);
            }
        });

        view.getPersonList().getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                bindPersonProfile(newValue);
            } else {
                unbindPersonProfile();
            }
        });

        view.getPersonProfile().getDeleteButton().setOnAction(e -> {
            var selectedItem = view.getPersonList().getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                // Delete the person from the DB
                repo.deletePerson(selectedItem);
            }
            // Clear the selection, this will cause unbindPersonProfile() to run
            view.getPersonList().getSelectionModel().clearSelection();
            // Delete the person from the list
            persons.remove(selectedItem);
        });

        view.getDisplayAllButton().setOnAction(e -> {
            var result = repo.getAllPersons();
            persons.setAll(result);
        });
    }

    private void bindPersonProfile(Person p) {
        ObservableList<Contact> closeContacts = FXCollections.observableArrayList(repo.getCloseContacts(p.getID()));
        var profile = view.getPersonProfile();
        profile.getNameText().setText(p.getName().toString());
        profile.getEmailText().setText(p.getEmail());
        profile.getPhoneText().setText(p.getPhoneNumber());
        profile.getCloseContacts().setItems(closeContacts);
    }

    private void unbindPersonProfile() {
        var profile = view.getPersonProfile();
        profile.getNameText().setText("");
        profile.getPhoneText().setText("");
        profile.getEmailText().setText("");
        profile.getCloseContacts().getItems().clear();
    }

    public Parent getView() {
        return view.getRoot();
    }
}
