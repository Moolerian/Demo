package util;

import gov.nasa.worldwind.View;
import gov.nasa.worldwind.awt.WorldWindowGLJPanel;
import gov.nasa.worldwind.geom.LatLon;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.geom.Sector;
import gov.nasa.worldwind.render.SurfaceImage;
import gov.nasa.worldwindx.examples.RubberSheetImage;
import javafx.embed.swing.SwingNode;
import javafx.scene.control.Alert;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by mohammad on 8/13/2016.
 */
public class Utils {

    public static SwingNode buildWW(WorldWindowGLJPanel wwj) {
        SwingNode node = new SwingNode();

        SwingUtilities.invokeLater(() -> {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            JPanel panel = new JPanel();
            panel.setPreferredSize(screenSize);
            panel.setLayout(new BorderLayout());
            panel.add(wwj);
            node.setContent(panel);
        });
        return node;

    }

    //TODO get a position and size of image
    public static RubberSheetImage.SurfaceImageEntry addSurfaceImage(File file, WorldWindowGLJPanel wwj) {
        RubberSheetImage.SurfaceImageEntry surfaceImageEntry = null;
        BufferedImage image;
        try {
            image = ImageIO.read(file);
            Position position =  new Position(LatLon.fromDegrees(35.746179170384686d, 51.20007936255699d), 0d);
            double lat = position.getLatitude().radians;
            double lon = position.getLongitude().radians;
            double sizeInMeters = 50000;
            double arcLength = sizeInMeters / wwj.getModel().getGlobe().getRadiusAt(position);
            Sector sector = Sector.fromRadians(lat - arcLength, lat + arcLength, lon - arcLength, lon + arcLength);
            surfaceImageEntry = new RubberSheetImage.SurfaceImageEntry(wwj, new SurfaceImage(image, sector), file.getName());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("اخطار");
            alert.setHeaderText("Look, a Warning Dialog");
            alert.setContentText("Careful with the next step!");
            alert.showAndWait();
        }

        return surfaceImageEntry;
    }

    public static void gotToPosition(WorldWindowGLJPanel wwj , Position position , double elevation){
        View view = wwj.getView();
        view.goTo(position, elevation);
    }
}
