package controller;

import gov.nasa.worldwind.Model;
import gov.nasa.worldwind.View;
import gov.nasa.worldwind.WorldWind;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.awt.WorldWindowGLJPanel;
import gov.nasa.worldwind.geom.LatLon;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.layers.Layer;
import gov.nasa.worldwind.layers.LayerList;
import gov.nasa.worldwindx.examples.RubberSheetImage;
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

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class RootController implements Initializable {
    private WorldWindowGLJPanel wwj;
    private Layer worldMapLayer;
    private Layer CompassLayer;
    private RubberSheetImage.SurfaceImageEntry surfaceImageEntry;

    // properties
    double iranLat = 35.746179170384686d;
    double iranLon = 51.20007936255699d;
    double iranElv = 51.20007936255699d;

    @FXML
    private ComboBox<Shape> comboBox;
    @FXML
    private BorderPane borderPane;

    // TODO could you show a confirm dialog before closing ?
    @FXML
    private void closeHandler() {
        System.exit(0);
    }

    //TODO create an input for elevating by user's input (lat,lon)
    @FXML
    private void elevateToIran() {
        Position iranPosition = new Position(LatLon.fromDegrees(iranLat, iranLon), 0d);
        Utils.gotToPosition(wwj, iranPosition, iranElv);
    }

    @FXML
    private void activeClickAndGo(ActionEvent e) {
        CheckBox activeClickAndGo = (CheckBox) e.getSource();
        if (activeClickAndGo.isSelected()) {
            wwj.getModel().getLayers().add(worldMapLayer);
        } else {
            wwj.getModel().getLayers().remove(worldMapLayer);
        }
    }

    @FXML
    private void editShapeToggle(ActionEvent e) {
        CheckBox editShapeToggle = (CheckBox) e.getSource();
        if (surfaceImageEntry != null) {
            if (editShapeToggle.isSelected()) {
                // TODO ask about if they want to resize, i will be able to do it
                surfaceImageEntry.getEditor().setArmed(true);
                LayerList layers = wwj.getModel().getLayers();
                Layer markerLayer = wwj.getModel().getLayers().getLayerByName("Marker Layer");
                layers.remove(markerLayer);
            } else {
                surfaceImageEntry.getEditor().setArmed(false);
            }
        }
    }

    @FXML
    private void compassLayerToggle(ActionEvent e) {
        CheckBox compassLayerToggle = (CheckBox) e.getSource();
        if (compassLayerToggle.isSelected()) {
            wwj.getModel().getLayers().add(CompassLayer);
        } else {
            wwj.getModel().getLayers().remove(CompassLayer);
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        wwj = new WorldWindowGLJPanel();
        Model m = (Model) WorldWind.createConfigurationComponent(AVKey.MODEL_CLASS_NAME);
        wwj.setModel(m);

        // remove WorldMap in order to make it in toggle manner
        worldMapLayer = wwj.getModel().getLayers().getLayerByName("World Map");
        wwj.getModel().getLayers().remove(worldMapLayer);

        CompassLayer = wwj.getModel().getLayers().getLayerByName("Compass");
        wwj.getModel().getLayers().remove(CompassLayer);


        //TODO fetch all other shapes from database and add in foreach to #comboBox
        ObservableList<Shape> shapes = FXCollections.observableArrayList();

        Shape selectOneOption = new Shape();
        selectOneOption.setId(null);
        selectOneOption.setDisplayName(resources.getString("select.one"));

        Shape toopShape = new Shape();
        toopShape.setId(1);
        toopShape.setDisplayName("توپ");

        shapes.add(selectOneOption);
        shapes.add(toopShape);

        comboBox.setItems(shapes);
        comboBox.setValue(selectOneOption);

        // TODO replace with lambda expression
        comboBox.valueProperty().addListener(new ChangeListener<Shape>() {
            @Override
            public void changed(ObservableValue<? extends Shape> observable, Shape oldValue, Shape newValue) {
                if (newValue.getId() != null) {
                    File toopShapeFile = new File("src/resource/images/Toop.png");
                    // TODO what would be happened with more than one file ?
                    surfaceImageEntry = Utils.addSurfaceImage(toopShapeFile, wwj);
                }
            }
        });

        borderPane.setRight(Utils.buildWW(wwj));

    }


}
