package controller;

import gov.nasa.worldwind.Model;
import gov.nasa.worldwind.View;
import gov.nasa.worldwind.WorldWind;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.awt.WorldWindowGLJPanel;
import gov.nasa.worldwind.geom.LatLon;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.layers.Layer;
import gov.nasa.worldwind.layers.WorldMapLayer;
import gov.nasa.worldwindx.examples.ClickAndGoSelectListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import model.Shape;
import util.Utils;

import java.net.URL;
import java.util.ResourceBundle;

public class RootController implements Initializable {
    private WorldWindowGLJPanel wwj;
    private Layer worldMapLayer;

    @FXML
    private ComboBox<Shape> comboBox;
    @FXML
    private BorderPane borderPane;

    // TODO could you show a confirm dialog before closing ?
    @FXML
    private void closeHandler() {
        System.exit(0);
    }

    @FXML
    private void elevateToIran() {
        View view = wwj.getView();
        Position matterhorn = new Position(LatLon.fromDegrees(35.746179170384686d, 51.20007936255699d), 0d);
        view.goTo(matterhorn, 3000000d);
    }

    // TODO find a better way for doing this
    @FXML
    private void activeClickAndGo(ActionEvent e) {
        CheckBox activeClickAndGo = (CheckBox) e.getSource();
        if (activeClickAndGo.isSelected()) {
            wwj.getModel().getLayers().add(worldMapLayer);
        } else {
            wwj.getModel().getLayers().remove(worldMapLayer);
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        wwj = new WorldWindowGLJPanel();
        Model m = (Model) WorldWind.createConfigurationComponent(AVKey.MODEL_CLASS_NAME);
        wwj.setModel(m);

        worldMapLayer = wwj.getModel().getLayers().getLayerByName("World Map");
        wwj.getModel().getLayers().remove(worldMapLayer);


        Shape selectOneOption = new Shape();
        selectOneOption.setId(null);
        selectOneOption.setDisplayName("انتخاب کنید");
        comboBox.setValue(selectOneOption);

        //TODO fetch all other shapes from database and add in foreach to #comboBox
        ObservableList<Shape> shapes = FXCollections.observableArrayList();

        // TODO replace with lambda expression
        comboBox.valueProperty().addListener(new ChangeListener<Shape>() {
            @Override
            public void changed(ObservableValue<? extends Shape> observable, Shape oldValue, Shape newValue) {
                System.out.println(newValue);
            }
        });

        borderPane.setRight(Utils.buildWW(wwj));

    }

}
