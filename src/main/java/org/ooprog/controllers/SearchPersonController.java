package org.ooprog.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import org.ooprog.models.Contact;
import org.ooprog.models.Person;
import org.ooprog.repositories.IPersonRepository;
import org.ooprog.views.SearchPersonView;

import java.time.LocalDate;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

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
        setProfileListeners();
    }

    private void setListeners() {
        view.getSearchButton().setOnAction(e -> {
            var searchString = view.getSearchField().getText();
            if (!searchString.isBlank()) {
                persons.setAll(repo.findPerson(searchString));
            }
        });

        view.getPersonList().getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                bindPersonProfile(newValue);
            } else {
                unbindPersonProfile();
            }
        });

        view.getDisplayAllButton().setOnAction(e -> {
            persons.setAll(repo.getAllPersons());
        });
    }

    private void setProfileListeners() {
        var profile = view.getPersonProfile();
        profile.getCurrentStatus().visibleProperty().bind(profile.getIsFiltered().selectedProperty());
        profile.getDeleteButton().setOnAction(e -> {
            var selectedItem = view.getPersonList().getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                // Delete the person from the DB
                repo.deletePerson(selectedItem);
            }
            // Clear the selection, this will cause unbindPersonProfile() to run
            view.getPersonList().getSelectionModel().clearSelection();
            // Refresh the list
            persons.setAll(repo.getAllPersons());
        });

        profile.getIsFiltered().selectedProperty().addListener((observableValue, oldValue, newValue) -> {
            var selectedPerson = view.getPersonList().getSelectionModel().getSelectedItem();
            if (selectedPerson != null) {
                // Check if the checkbox has been selected
                if (newValue) {
                    profile.getFilterButton().fire();
                } else {
                    // Rebind the profile to revert back to the original list
                    bindPersonProfile(selectedPerson);
                }
            }
        });

        profile.getFilterButton().setOnAction(e -> {
            profile.getIsFiltered().setSelected(true);
            var selectedPerson = view.getPersonList().getSelectionModel().getSelectedItem();
            if (selectedPerson != null) {
                var date = profile.getFilterDatePicker().getValue();
                var filtered = repo.getCloseContacts(selectedPerson.getID())
                        .stream().filter(q -> q.getDateContact()
                                .isAfter(date) || q.getDateContact().isEqual(date)).collect(Collectors.toList());
                profile.getCloseContactsTable().setItems(FXCollections.observableArrayList(filtered));

                var diff = DAYS.between(LocalDate.now(), date);
                if (diff == 0) {
                    profile.getCurrentStatus().setText("You are viewing close contacts from today.");
                } else if (diff < 0) {
                    profile.getCurrentStatus().setText("You are viewing last " + Math.abs(diff) + " days.");
                } else {
                    profile.getCurrentStatus().setText("You are viewing " + diff + " days into the future.");
                }
            }
        });

        profile.getFilterDatePicker().valueProperty().addListener((observableValue, oldValue, newValue) -> {
        });

        profile.getLastWeekButton().setOnAction(e -> {
            profile.getFilterDatePicker().setValue(LocalDate.now().minusDays(7));
        });

        profile.getLastTwoWeeksButton().setOnAction(e -> {
            profile.getFilterDatePicker().setValue(LocalDate.now().minusDays(14));
        });
    }

    private void bindPersonProfile(Person p) {
        ObservableList<Contact> closeContacts = FXCollections.observableArrayList(repo.getCloseContacts(p.getID()));
        var profile = view.getPersonProfile();
        profile.getNameText().setText(p.getName().toString());
        profile.getEmailText().setText(p.getEmail());
        profile.getPhoneText().setText(p.getPhoneNumber());
        profile.getCloseContactsTable().setItems(closeContacts);
        // Clear filter checkbox
        profile.getIsFiltered().setSelected(false);
    }

    private void unbindPersonProfile() {
        var profile = view.getPersonProfile();
        profile.getNameText().setText("");
        profile.getPhoneText().setText("");
        profile.getEmailText().setText("");
        profile.getCloseContactsTable().getItems().clear();
        // Clear filter checkbox
        profile.getIsFiltered().setSelected(false);
    }

    public Parent getView() {
        return view.getRoot();
    }
}
