package org.ooprog.views;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.util.Callback;
import org.ooprog.models.Contact;
import org.ooprog.models.Person;

public class PersonProfileView {
    private final GridPane root = new GridPane();
    private final Text nameText = new Text();
    private final Text emailText = new Text();
    private final Text phoneText = new Text();
    private final ListView<Contact> closeContacts = new ListView<>();
    private final Button deleteButton = new Button("Delete");

    public PersonProfileView() {
        buildLayout();
    }

    private void buildLayout() {
        var nameLabel = new Label("Name: ");
        var emailLabel = new Label("Email: ");
        var phoneNoLabel = new Label("Phone: ");

        var nameBox = new HBox(nameLabel, nameText);
        var emailBox = new HBox(emailLabel, emailText);
        var phoneBox = new HBox(phoneNoLabel, phoneText);

        var contactBox = new VBox(nameBox, emailBox, phoneBox, deleteButton);
        var contactsPane = new VBox();
        contactsPane.getChildren().add(closeContacts);

        var col1 = new ColumnConstraints();
        col1.setPercentWidth(50);
        var col2 = new ColumnConstraints();
        col2.setPercentWidth(50);

        // Make each column take 50% of width
        root.getColumnConstraints().addAll(col1, col2);
        root.addColumn(0, contactBox);
        root.addColumn(1, contactsPane);

        closeContacts.setCellFactory(contactListView -> new CustomListItem());
    }

    public Parent getRoot() {
        return root;
    }

    public Text getNameText() {
        return nameText;
    }

    public Text getEmailText() {
        return emailText;
    }

    public Text getPhoneText() {
        return phoneText;
    }

    public ListView<Contact> getCloseContacts() {
        return closeContacts;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }
}
