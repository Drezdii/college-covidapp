import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.ooprog.controllers.AddCloseContactController;
import org.ooprog.models.Name;
import org.ooprog.models.Person;
import org.ooprog.views.AddCloseContactView;
import org.ooprog.views.CreatePersonView;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.time.LocalDate;

@ExtendWith(ApplicationExtension.class)
public class TestSuite_AddCloseContact {
    AddCloseContactView view;
    AddCloseContactController controller;

    @Start
    private void start(Stage stage) {
        var t = new TestingRepostiory();
        view = new AddCloseContactView();
        controller = new AddCloseContactController(view, t);
        stage.setScene(new Scene(new VBox(view.getRoot()), 100, 100));
        stage.show();
    }

    @Test
    void button_contains_text_save(FxRobot robot) {
        Assertions.assertThat(view.getSaveButton()).hasText("Save");
    }

    @Test
    void add_contact_returns_null_on_same_person() {
        var p = new Person(1, new Name("T", "T", "T"), "test@test.com", "0999999");
        org.junit.jupiter.api.Assertions.assertNull(controller.addCloseContact(p, p, LocalDate.now()));
    }
}
