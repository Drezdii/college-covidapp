package org.ooprog.views;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.ooprog.models.Person;

import java.time.LocalDate;

public class AddCloseContactView {
    private final VBox root = new VBox();

    private final ListView<Person> leftList = new ListView<>();
    private final ListView<Person> rightList = new ListView<>();
    private final DatePicker datePicker = new DatePicker(LocalDate.now());
    private final Button saveButton = new Button("Save");

    public AddCloseContactView() {
        buildLayout();
    }

    private void buildLayout() {
        var listsContainer = new HBox();

        // Make the lists grow to fill the whole width
        HBox.setHgrow(leftList, Priority.ALWAYS);
        HBox.setHgrow(rightList, Priority.ALWAYS);

        listsContainer.getChildren().addAll(leftList, rightList);
        root.getChildren().addAll(listsContainer, datePicker, saveButton);
    }

    // Return the view associated with this controller
    public Parent getRoot() {
        return root;
    }

    public ListView<Person> getLeftList() {
        return leftList;
    }

    public ListView<Person> getRightList() {
        return rightList;
    }

    public DatePicker getDatePicker() {
        return datePicker;
    }

    public Button getSaveButton() {
        return saveButton;
    }
}
