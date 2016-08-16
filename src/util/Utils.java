package util;

import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.awt.WorldWindowGLJPanel;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.geom.Sector;
import gov.nasa.worldwind.render.SurfaceImage;
import gov.nasa.worldwindx.examples.RubberSheetImage;
import gov.nasa.worldwindx.examples.util.ShapeUtils;
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

    public static RubberSheetImage.SurfaceImageEntry addSurfaceImage(File file, WorldWindowGLJPanel wwj) {
        RubberSheetImage.SurfaceImageEntry surfaceImageEntry = null;
        BufferedImage image;
        try {
            image = ImageIO.read(file);
            Position position = ShapeUtils.getNewShapePosition(wwj);
            double lat = position.getLatitude().radians;
            double lon = position.getLongitude().radians;
            double sizeInMeters = ShapeUtils.getViewportScaleFactor(wwj);
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
}
