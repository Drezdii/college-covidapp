package org.ooprog.controllers;

import javafx.scene.Parent;
import org.ooprog.models.Name;
import org.ooprog.models.Person;
import org.ooprog.repositories.IPersonRepository;
import org.ooprog.views.CreatePersonView;

public class CreatePersonController {
    private final CreatePersonView view;
    private final IPersonRepository repo;

    public CreatePersonController(CreatePersonView view, IPersonRepository repository) {
        this.view = view;
        repo = repository;
        setListeners();
    }

    private void setListeners() {
        view.getSaveButton().setOnAction(e -> addPerson());
    }

    private void addPerson() {
        var fName = view.getFirstNameField().getText();
        var mName = view.getMiddleNameField().getText();
        var lName = view.getLastNameField().getText();
        var email = view.getEmailField().getText();
        var phoneNo = view.getPhoneField().getText();

        // Check if required fields have value
        if (fName.isBlank() || lName.isBlank() || phoneNo.isBlank()) {
            return;
        }

        var name = new Name(fName, mName, lName);
        var p = new Person(0, name, email, phoneNo);
        repo.createPerson(p);
    }

    public Parent getView() {
        return view.getRoot();
    }
}
