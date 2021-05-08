package org.ooprog.views;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import org.ooprog.models.Person;

public class SearchPersonView {
    private final GridPane root = new GridPane();

    private final Label searchLabel = new Label("Search using name or ID");
    private final Label displayAllLabel = new Label("Display all people");
    private final TextField searchField = new TextField();
    private final Button searchButton = new Button("Search");
    private final Button displayAllButton = new Button("Load");
    private final ListView<Person> personList = new ListView<>();
    private final PersonProfileView personProfile = new PersonProfileView();

    public SearchPersonView() {
        buildLayout();
    }

    private void buildLayout() {
        var leftPanel = new VBox();
        VBox.setVgrow(leftPanel, Priority.ALWAYS);
        var col2 = new ColumnConstraints();
        col2.setPercentWidth(50);

        var col1 = new ColumnConstraints();
        col1.setPercentWidth(50);
        root.getColumnConstraints().addAll(col1, col2);

        var searchBox = new HBox();
        searchBox.getChildren().addAll(searchField, searchButton);

        var displayAllBox = new HBox();
        displayAllBox.getChildren().addAll(displayAllButton, displayAllLabel);

        leftPanel.getChildren().addAll(searchLabel, searchBox, displayAllBox, personList);
        var rightPanel = new VBox();
        rightPanel.getChildren().add(personProfile.getRoot());
        root.addColumn(0, leftPanel);
        root.addColumn(1, rightPanel);
    }

    // Return the view associated with this controller
    public Parent getRoot() {
        return root;
    }

    public Label getSearchLabel() {
        return searchLabel;
    }

    public TextField getSearchField() {
        return searchField;
    }

    public Button getSearchButton() {
        return searchButton;
    }

    public ListView<Person> getPersonList() {
        return personList;
    }

    public PersonProfileView getPersonProfile() {
        return personProfile;
    }

    public Button getDisplayAllButton() {
        return displayAllButton;
    }
}
