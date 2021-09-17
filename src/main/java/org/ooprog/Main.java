package org.ooprog;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.ooprog.controllers.AddCloseContactController;
import org.ooprog.controllers.CreatePersonController;
import org.ooprog.controllers.SearchPersonController;
import org.ooprog.models.Person;
import org.ooprog.repositories.PersonRepository;
import org.ooprog.views.AddCloseContactView;
import org.ooprog.views.CreatePersonView;
import org.ooprog.views.SearchPersonView;

import java.util.ArrayList;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Get the DB session
        var dbSession = HibernateSessionFactory.getSession();
        // Inject the session into the repository
        var personRepo = new PersonRepository(dbSession);

        TabPane paneContainer = new TabPane();
        paneContainer.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        VBox root = new VBox(paneContainer);
        VBox.setVgrow(root, Priority.ALWAYS);

        var personController = new CreatePersonController(new CreatePersonView(), personRepo);
        var contactsController = new AddCloseContactController(new AddCloseContactView(), personRepo);
        var searchController = new SearchPersonController(new SearchPersonView(), personRepo);

        var personTab = new Tab("Create person");
        personTab.setContent(personController.getView());

        var contactsTab = new Tab("Record a contact");
        contactsTab.setContent(contactsController.getView());

        var searchTab = new Tab("Search");
        searchTab.setContent(searchController.getView());

        paneContainer.getTabs().addAll(personTab, contactsTab, searchTab);
        primaryStage.setTitle("COVID APP");

        var crashButton = new Button("Crash the app");
        crashButton.setOnAction(e -> runOutOfMemory());
        root.getChildren().add(crashButton);

        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.show();
    }

    private void runOutOfMemory() {
        var crashList = new ArrayList<Person>();
        while (true) {
            crashList.add(new Person());
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
