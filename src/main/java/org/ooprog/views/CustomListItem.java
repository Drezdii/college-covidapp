package org.ooprog.views;

import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import org.ooprog.models.Contact;

public class CustomListItem extends ListCell<Contact> {
    private HBox root = new HBox();
    private Text name1 = new Text();
    private Text name2 = new Text();
    private Text date = new Text();

    public CustomListItem() {
        super();
        root.getChildren().addAll(name1, name2, date);
    }

    @Override
    protected void updateItem(Contact contact, boolean empty) {
        super.updateItem(contact, empty);
        if (contact != null && !empty) {
            name1.setText(contact.getPerson1().toString());
            name2.setText(contact.getPerson2().toString());
            date.setText(contact.getDateContact().toString());
            setGraphic(root);
        } else {
            setGraphic(null);
        }
    }
}
