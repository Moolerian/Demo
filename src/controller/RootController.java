package controller;


import gov.nasa.worldwind.Model;
import gov.nasa.worldwind.WorldWind;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.awt.WorldWindowGLJPanel;
import gov.nasa.worldwind.geom.LatLon;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.layers.Layer;
import gov.nasa.worldwind.layers.WorldMapLayer;
import gov.nasa.worldwindx.examples.ClickAndGoSelectListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Shape;
import util.DBUtils;
import util.UTF8Control;
import util.Utils;

import java.awt.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import static util.Constants.*;

public class RootController implements Initializable {
    private static WorldWindowGLJPanel wwj;
    private Layer worldMapLayer;
    private Layer compassLayer;
    private Layer scaleLayer;

    @FXML
    private BorderPane borderPane;
    @FXML
    private ResourceBundle messages;
    @FXML
    private Label utcClock;
    @FXML
    private Label utcClockLabel;
    @FXML
    private Label localClock;
    @FXML
    private Label localClockLabel;

    @FXML
    private void closeHandler() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(messages.getString("close.app.dialog.header"));
        alert.setHeaderText(null);
        alert.setContentText(messages.getString("are.you.sure.for.closing"));
        ButtonType ok = new ButtonType(messages.getString("button.type.ok"));
        ButtonType cancel = new ButtonType(messages.getString("button.type.cancel"));
        alert.getButtonTypes().clear();
        alert.getButtonTypes().add(cancel);
        alert.getButtonTypes().add(ok);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ok) {
            System.exit(0);
        } else {
            alert.close();
        }
    }

    @FXML
    private void elevateToIran() {
        Position iranPosition = new Position(LatLon.fromDegrees(iranLat, iranLon), 0d);
        Utils.gotToPosition(wwj, iranPosition, iranElv);
    }

    @FXML
    private void goToLatLon() {
        try {
            UTF8Control utf8Control = new UTF8Control();
            ResourceBundle bundle = utf8Control.newBundle("resource/ApplicationResources",
                    new Locale("fa"), null,
                    ClassLoader.getSystemClassLoader(), true);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/latLonDialog.fxml"), bundle);
            BorderPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setResizable(false);
            dialogStage.initOwner(MainDemo.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            LatLonDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void compassLayerToggle(ActionEvent e) {
        CheckMenuItem compassLayerToggle = (CheckMenuItem) e.getSource();
        if (compassLayerToggle.isSelected()) {
            wwj.getModel().getLayers().add(compassLayer);
        } else {
            wwj.getModel().getLayers().remove(compassLayer);
        }
    }

    @FXML
    private void ScaleLayerToggle(ActionEvent e) {
        CheckMenuItem scaleLayerToggle = (CheckMenuItem) e.getSource();
        if (scaleLayerToggle.isSelected()) {
            wwj.getModel().getLayers().add(scaleLayer);
        } else {
            wwj.getModel().getLayers().remove(scaleLayer);
        }
    }

    @FXML
    private void helpDialog() {
        try {
            UTF8Control utf8Control = new UTF8Control();
            ResourceBundle bundle = utf8Control.newBundle("resource/ApplicationResources",
                    new Locale("fa"), null,
                    ClassLoader.getSystemClassLoader(), true);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/help.fxml"), bundle);
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setResizable(false);
            dialogStage.initOwner(MainDemo.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void activeWorldMap(ActionEvent e) {
        CheckMenuItem activeClickAndGo = (CheckMenuItem) e.getSource();
        if (activeClickAndGo.isSelected()) {
            wwj.getModel().getLayers().add(worldMapLayer);
            wwj.addSelectListener(new ClickAndGoSelectListener(wwj, WorldMapLayer.class));
        } else {
            wwj.getModel().getLayers().remove(worldMapLayer);
        }
    }


//    // TODO Move to icon Bar
//    @FXML
//    private void shapeAreaAction() {
//        try {
//            UTF8Control utf8Control = new UTF8Control();
//            ResourceBundle bundle = utf8Control.newBundle("resource/ApplicationResources",
//                    new Locale("fa"), null, ClassLoader.getSystemClassLoader(), true);
//
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/positionDialog.fxml"), bundle);
//            AnchorPane page = loader.load();
//
//            Position referencePosition = surfaceImageEntry.getSurfaceImage().getReferencePosition();
//            TextField latitudeField = (TextField) page.lookup("#latitudeField");
//            latitudeField.setText(String.valueOf(referencePosition.getLatitude()));
//            TextField longitudeField = (TextField) page.lookup("#longitudeField");
//            longitudeField.setText(String.valueOf(referencePosition.getLongitude()));
//
//            // Create the dialog Stage.
//            Stage dialogStage = new Stage();
//            dialogStage.initModality(Modality.APPLICATION_MODAL);
//            dialogStage.setResizable(false);
//            dialogStage.setIconified(false);
//            dialogStage.initOwner(MainDemo.getPrimaryStage());
//            Scene scene = new Scene(page);
//            dialogStage.setScene(scene);
//
//
//            // Show the dialog and wait until the user closes it
//            dialogStage.showAndWait();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    private void initializeComponent() {

        wwj = new WorldWindowGLJPanel();
        Model m = (Model) WorldWind.createConfigurationComponent(AVKey.MODEL_CLASS_NAME);
        wwj.setModel(m);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        utcClockLabel.setTranslateX(screenSize.getWidth() / 2);
        utcClock.setTranslateX(screenSize.getWidth() / 2 - 10);

        Utils.showTime(utcClock, true);
        Utils.showTime(localClock, false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeComponent();

        messages = resources;
        // remove WorldMap and Compass layer in order to make it in toggle manner
        // TODO move it to a separate method
        worldMapLayer = wwj.getModel().getLayers().getLayerByName("World Map");
        wwj.getModel().getLayers().remove(worldMapLayer);
        compassLayer = wwj.getModel().getLayers().getLayerByName("Compass");
        wwj.getModel().getLayers().remove(compassLayer);
        scaleLayer = wwj.getModel().getLayers().getLayerByName("Scale bar");
        wwj.getModel().getLayers().remove(scaleLayer);

        // TODO Remove this part and use a simple colored circle
        ObservableList<Shape> shapes = FXCollections.observableArrayList();

        Shape selectOneOption = new Shape();
        selectOneOption.setId(null);
        selectOneOption.setDisplayName(resources.getString("select.one"));
        shapes.add(selectOneOption);
        try {
            shapes.addAll(DBUtils.getAllShapes());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        borderPane.setRight(Utils.buildWW(wwj));

    }


    static WorldWindowGLJPanel getWwj() {
        return wwj;
    }

}
