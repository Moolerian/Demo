package fx.sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Created by mohammad on 8/10/2016.
 */
public class ThreeComboBox extends Application {

    @Override
    public void start(Stage primaryStage) {

        List combox1List = new ArrayList();
        for (int i = 1; i < 10; i++) {
            combox1List.add(i);
        }

        final Map combox2Map = new HashMap();

        for (int i = 0; i < combox1List.size(); i++) {
            List l = new ArrayList();
            for (int j = 1; j < 10; j++) {
                int k = (Integer) combox1List.get(i) * 10 + j;
                l.add(k);
            }
            combox2Map.put(combox1List.get(i), l);
        }
        final Map combox3Map = new HashMap();
        for (Object o : combox1List) {
            for (Object o1 : (List) combox2Map.get(o)) {
                List l = new ArrayList();
                for (int i = 1; i < 10; i++) {
                    int value = (Integer) o1 * 10 + i;
                    l.add(value);
                }
                combox3Map.put(o1, l);
            }
        }
        ObservableList combox1 = FXCollections.observableList(combox1List);
        HBox box = new HBox(20);
        box.setPadding(new Insets(20, 20, 20, 20));
        ComboBox cb1 = new ComboBox();
        final ComboBox cb2 = new ComboBox();
        final ComboBox cb3 = new ComboBox();
        cb1.setItems(combox1);
        cb1.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
                ObservableList combox2 = FXCollections.observableArrayList((List) combox2Map.get(t1));
                cb2.setItems(combox2);
            }
        });

        cb2.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
                if (t1 != null) {
                    ObservableList combox3 = FXCollections.observableArrayList((List) combox3Map.get(t1));
                    cb3.setItems(combox3);
                }
            }
        });
        box.getChildren().addAll(cb1, cb2, cb3);
        Scene scene = new Scene(box, 300, 250);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}