package org.ooprog.controllers;

import javafx.scene.Parent;
import org.ooprog.views.AddCloseContactView;

public class AddCloseContactController {
    private final AddCloseContactView view;

    public AddCloseContactController(AddCloseContactView view) {
        this.view = view;
    }

    public Parent getView() {
        return view.getRoot();
    }
}
