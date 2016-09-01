package util;

import gov.nasa.worldwind.View;
import gov.nasa.worldwind.awt.WorldWindowGLJPanel;
import gov.nasa.worldwind.geom.LatLon;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.geom.Sector;
import gov.nasa.worldwind.render.SurfaceImage;
import gov.nasa.worldwind.util.Logging;
import gov.nasa.worldwindx.examples.RubberSheetImage;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.util.Duration;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Calendar;
import java.util.TimeZone;

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
            Position position = new Position(LatLon.fromDegrees(35.746179170384686d, 51.20007936255699d), 0d);
            double lat = position.getLatitude().radians;
            double lon = position.getLongitude().radians;
            double sizeInMeters = 50000;
            double arcLength = sizeInMeters / wwj.getModel().getGlobe().getRadiusAt(position);
            Sector sector = Sector.fromRadians(lat - arcLength, lat + arcLength, lon - arcLength, lon + arcLength);
            surfaceImageEntry = new RubberSheetImage.SurfaceImageEntry(wwj, new SurfaceImage(image, sector), file.getName());
            wwj.getModel().getLayers();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("اخطار");
            alert.setHeaderText("Look, a Warning Dialog");
            alert.setContentText("Careful with the next step!");
            alert.showAndWait();
        }

        return surfaceImageEntry;
    }

    public static void gotToPosition(WorldWindowGLJPanel wwj, Position position, double elevation) {
        View view = wwj.getView();
        view.goTo(position, elevation);
    }

    public static String pad(int fieldWidth, char padChar, String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = s.length(); i < fieldWidth; i++) {
            sb.append(padChar);
        }
        sb.append(s);

        return sb.toString();
    }

    public static void showTime(javafx.scene.control.Label showingLable, boolean isUTC) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0),
                        actionEvent -> {
                            Calendar time;
                            if (isUTC) {
                                time = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                            } else {
                                time = Calendar.getInstance();
                            }
                            String hourString = Utils.pad(2, ' ', time.get(Calendar.HOUR) == 0 ? "12" : time.get(Calendar.HOUR) + "");
                            String minuteString = Utils.pad(2, '0', time.get(Calendar.MINUTE) + "");
                            String secondString = Utils.pad(2, '0', time.get(Calendar.SECOND) + "");
                            String ampmString = time.get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM";
                            showingLable.setText(hourString + ":" + minuteString + ":" + secondString + " " + ampmString);
                        }
                ),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    static {
        try {
            String architecture = System.getProperty("os.arch");
            if ("x86".equals(architecture))
                System.loadLibrary("WebView32");
            else
                System.loadLibrary("WebView64");
        } catch (Throwable t) {
            String message = Logging.getMessage("WebView.ExceptionCreatingWebView", t);
            Logging.logger().severe(message);
        }
    }
}
