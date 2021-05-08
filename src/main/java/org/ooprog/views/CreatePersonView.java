package org.ooprog.views;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class CreatePersonView {
    private final GridPane root = new GridPane();

    private final Label fNameLabel = new Label("First name:");
    private final Label mNameLabel = new Label("Middle name:");
    private final Label lNameLabel = new Label("Last name:");
    private final Label phoneLabel = new Label("Phone:");
    private final Label emailLabel = new Label("Email:");

    private final TextField fNameField = new TextField();
    private final TextField mNameField = new TextField();
    private final TextField lNameField = new TextField();
    private final TextField phoneField = new TextField();
    private final TextField emailField = new TextField();

    private final Button saveButton = new Button("Create");

    public CreatePersonView() {
        buildLayout();
    }

    private void buildLayout() {
        root.addColumn(1, fNameLabel, mNameLabel, lNameLabel, phoneLabel, emailLabel, saveButton);
        root.addColumn(2, fNameField, mNameField, lNameField, phoneField, emailField);
    }

    // Return the view associated with this controller
    public Parent getRoot() {
        return root;
    }

    public TextField getFirstNameField() {
        return fNameField;
    }

    public TextField getMiddleNameField() {
        return mNameField;
    }

    public TextField getLastNameField() {
        return lNameField;
    }

    public TextField getPhoneField() {
        return phoneField;
    }

    public TextField getEmailField() {
        return emailField;
    }

    public Button getSaveButton() {
        return saveButton;
    }
}
