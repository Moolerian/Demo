package controller;

import gov.nasa.worldwind.Model;
import gov.nasa.worldwind.View;
import gov.nasa.worldwind.WorldWind;
import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.awt.WorldWindowGLJPanel;
import gov.nasa.worldwind.geom.LatLon;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.geom.Sector;
import gov.nasa.worldwind.layers.Layer;
import gov.nasa.worldwind.render.SurfaceImage;
import gov.nasa.worldwindx.examples.RubberSheetImage;
import gov.nasa.worldwindx.examples.util.ShapeUtils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import model.Shape;
import util.Utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

// TODO use resource bundle
public class RootController implements Initializable {
    private WorldWindowGLJPanel wwj;
    private Layer worldMapLayer;
    private RubberSheetImage.SurfaceImageEntry surfaceImageEntry;

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
        View view = wwj.getView();
        Position matterhorn = new Position(LatLon.fromDegrees(35.746179170384686d, 51.20007936255699d), 0d);
        view.goTo(matterhorn, 3000000d);
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        wwj = new WorldWindowGLJPanel();
        Model m = (Model) WorldWind.createConfigurationComponent(AVKey.MODEL_CLASS_NAME);
        wwj.setModel(m);

        // remove WorldMap in order to make it in toggle manner
        worldMapLayer = wwj.getModel().getLayers().getLayerByName("World Map");
        wwj.getModel().getLayers().remove(worldMapLayer);


        //TODO fetch all other shapes from database and add in foreach to #comboBox
        ObservableList<Shape> shapes = FXCollections.observableArrayList();

        Shape selectOneOption = new Shape();
        selectOneOption.setId(null);
        selectOneOption.setDisplayName("انتخاب کنید");

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
                    Utils.addSurfaceImage(toopShapeFile, wwj);
                }
            }
        });

        borderPane.setRight(Utils.buildWW(wwj));

    }


}
