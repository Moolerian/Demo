package controller;

import gov.nasa.worldwind.Model;
import gov.nasa.worldwind.WorldWind;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.awt.WorldWindowGLJPanel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import model.Shape;
import sun.swing.SwingUtilities2;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class RootController implements Initializable {

    @FXML
    private ComboBox<Shape> comboBox;
    @FXML
    private BorderPane borderPane;

    private WorldWindowGLJPanel wwj;

    @FXML
    private void closeHandler() {
        System.exit(0);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

        borderPane.setRight(buildWW());

    }

    //TODO move it to util
    private SwingNode buildWW() {
        SwingNode node = new SwingNode();

        SwingUtilities.invokeLater(() -> {
            wwj = new WorldWindowGLJPanel();
            Model m = (Model) WorldWind.createConfigurationComponent(AVKey.MODEL_CLASS_NAME);
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            wwj.setModel(m);
          //  wwj.setBounds(0, 0, 600, 500);
            JPanel panel = new JPanel();
            panel.setPreferredSize(screenSize);
            panel.setLayout(new BorderLayout());
            //panel.setBounds(200, 100, 600, 500);
            panel.add(wwj);
            node.setContent(panel);
            //node.relocate(170, 10);
        });
        return node;

    }

    private SwingNode text(){
        SwingNode node = new SwingNode();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                JPanel jPanel = new JPanel();
                JButton button = new JButton("sss");
                jPanel.setPreferredSize(screenSize);
                jPanel.setLayout(new BorderLayout());
                jPanel.setBackground(Color.black);
                jPanel.setForeground(Color.black);
                jPanel.add(button);
                node.setContent(jPanel);
            }
        });


        return node;
    }
}
