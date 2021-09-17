package org.ooprog.views;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.ooprog.models.Contact;
import org.ooprog.models.Person;

import java.time.LocalDate;

public class PersonProfileView {
    private final GridPane root = new GridPane();
    private final Text nameText = new Text();
    private final Text emailText = new Text();
    private final Text phoneText = new Text();
    private final Button deleteButton = new Button("Delete");
    private final DatePicker filterDatePicker = new DatePicker(LocalDate.now());
    private final Button lastWeekButton = new Button("Last 7 days");
    private final Button lastTwoWeeksButton = new Button("Last 14 days");
    private final Button filterButton = new Button("Filter");
    private final CheckBox isFiltered = new CheckBox("Show filtered data");
    private final Text currentStatus = new Text("");

    private final TableView<Contact> closeContactsTable = new TableView<>();

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
        contactsPane.getChildren().add(closeContactsTable);

        var col1 = new ColumnConstraints();
        col1.setPercentWidth(30);
        var col2 = new ColumnConstraints();
        col2.setPercentWidth(70);

        var contactsBox = new VBox();
        var filterBox = new VBox();
        var datePickerBox = new HBox();
        datePickerBox.getChildren().addAll(filterDatePicker, filterButton);

        var weeksBox = new HBox();

        weeksBox.getChildren().addAll(lastWeekButton, lastTwoWeeksButton, isFiltered);
        filterBox.getChildren().addAll(datePickerBox, weeksBox, currentStatus);

        contactsBox.getChildren().addAll(filterBox, closeContactsTable);


        root.setPadding(new Insets(16, 16, 0, 16));
        root.getColumnConstraints().addAll(col1, col2);

        TableColumn<Contact, Person> tab1 = new TableColumn<>("Person");
        TableColumn<Contact, Person> tab2 = new TableColumn<>("Contact with");
        TableColumn<Contact, Person> tab3 = new TableColumn<>("Date");

        // Allow sorting only on Date table column
        tab1.setSortable(false);
        tab2.setSortable(false);

        // Make the table columns take all available space
        closeContactsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tab1.setCellValueFactory(new PropertyValueFactory<>("person1"));
        tab2.setCellValueFactory(new PropertyValueFactory<>("person2"));
        tab3.setCellValueFactory(new PropertyValueFactory<>("dateContact"));

        closeContactsTable.getColumns().add(tab1);
        closeContactsTable.getColumns().add(tab2);
        closeContactsTable.getColumns().add(tab3);

        root.addColumn(0, contactBox);
        root.addColumn(1, contactsBox);
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

    public TableView<Contact> getCloseContactsTable() {
        return closeContactsTable;
    }

    public DatePicker getFilterDatePicker() {
        return filterDatePicker;
    }

    public Button getLastWeekButton() {
        return lastWeekButton;
    }

    public Button getLastTwoWeeksButton() {
        return lastTwoWeeksButton;
    }

    public Button getFilterButton() {
        return filterButton;
    }

    public CheckBox getIsFiltered() {
        return isFiltered;
    }

    public Text getCurrentStatus() {
        return currentStatus;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }
}
