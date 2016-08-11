package fx.sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingNode;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;

/**
 * Created by mohammad on 8/11/2016.
 */
public class Test extends Application {
    private JPanel jPanel = null;

    @Override
    public void start(Stage stage) throws Exception {
        if (Platform.isFxApplicationThread()) { // all of this happens on the fx application thread
            stage.setMaximized(true);

            VBox vbox = new VBox();
//            MenuBar menuBar = new MenuBar();
//            menuBar.getMenus().add(new Menu("bla"));
//            vbox.getChildren().add(menuBar);

            StackPane mainPane = new StackPane();
            mainPane.setPrefSize(9999, 9999); //Better way to ensure all space used?
            vbox.getChildren().add(mainPane);
            vbox.setVisible(true);

            Scene scene = new Scene(vbox, 9999, 9999); //Better way to ensure all space used?
            stage.setScene(scene);

            SwingNode swingNode = new SwingNode();
            mainPane.getChildren().add(swingNode);

            SwingUtilities.invokeLater(new Runnable() { //Use EDT to build swing components
                @Override
                public void run() {
                    jPanel = new JPanel();

                   // jPanel.setBackground(Color.WHITE);
                    jPanel.setLayout(new GridLayout(2, 1));
                    jPanel.add(new JPanel());

                    JLabel nonOpaquePanel = new JLabel("Bla");
                    nonOpaquePanel.setPreferredSize(new Dimension(5000, 5000));
                    nonOpaquePanel.setOpaque(false);
                    JScrollPane scrollPane = new JScrollPane(nonOpaquePanel);
                    scrollPane.setOpaque(false);
                    scrollPane.getViewport().setOpaque(false); //Black background -> Bug?
                    jPanel.add(scrollPane);
                }
            });

            Thread.sleep(1000); //terrible way to wait for the EDT, I know
            swingNode.setContent(jPanel); //Seems to interfere with painting of menu
            stage.show();

            SwingUtilities.invokeLater(new Runnable() { //I am pretty sure this isn't necessary, but whatever
                @Override
                public void run() {
                    jPanel.repaint();
                }
            });
        }
    }
}
