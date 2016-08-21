package controller;


import gov.nasa.worldwind.geom.LatLon;
import gov.nasa.worldwind.geom.Position;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.Utils;

import static util.Constants.iranElv;
import static util.Constants.iranLat;
import static util.Constants.iranLon;

/**
 *
 * @author mohammad
 */
public class LatLonDialogController {
    private Stage dialogStage;

    @FXML
    private TextField userPrefLatitude;
    @FXML
    private TextField userPrefLongitude;
    @FXML
    private TextField userPrefAltitude;



    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {

    }

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {

        double latitude = Double.parseDouble(userPrefLatitude.getText());
        double longitude = Double.parseDouble(userPrefLongitude.getText());
        double altitude = Double.parseDouble(userPrefAltitude.getText());
        Position iranPosition = new Position(LatLon.fromDegrees(latitude, longitude), 0d);
        dialogStage.close();
        Utils.gotToPosition(RootController.getWwj(), iranPosition, altitude);

    }



    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }


}
