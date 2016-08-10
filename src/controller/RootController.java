package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SingleSelectionModel;

import java.net.URL;
import java.util.ResourceBundle;

public class RootController implements Initializable {

    @FXML
    private ComboBox<Choice> comboBox;

    @FXML
    private void closeHandler() {
        System.exit(0);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Choice> choices = FXCollections.observableArrayList();
        choices.add(new Choice(null, "No selection"));
        comboBox.setItems(choices);

        comboBox.valueProperty().addListener(new ChangeListener<Choice>() {
            @Override
            public void changed(ObservableValue<? extends Choice> observable, Choice oldValue, Choice newValue) {
                System.out.println();
            }
        });


    }




    class Choice {
        Integer id;
        String displayString;

        Choice(Integer id) {
            this(id, null);
        }

        Choice(String displayString) {
            this(null, displayString);
        }

        Choice(Integer id, String displayString) {
            this.id = id;
            this.displayString = displayString;
        }

        @Override
        public String toString() {
            return displayString;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Choice choice = (Choice) o;
            return displayString != null && displayString.equals(choice.displayString) || id != null && id.equals(choice.id);
        }

        @Override
        public int hashCode() {
            int result = id != null ? id.hashCode() : 0;
            result = 31 * result + (displayString != null ? displayString.hashCode() : 0);
            return result;
        }
    }
}
