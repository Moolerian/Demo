package util;

import gov.nasa.worldwind.awt.WorldWindowGLJPanel;
import javafx.embed.swing.SwingNode;

import javax.swing.*;
import java.awt.*;

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
}
