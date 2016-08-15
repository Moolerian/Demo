import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.UTF8Control;

import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        UTF8Control utf8Control = new UTF8Control();
        ResourceBundle bundle = utf8Control.newBundle("resource/ApplicationResources",new Locale("fa"),null,ClassLoader.getSystemClassLoader(),true);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/root.fxml"),bundle);
        Parent root = loader.load();
//        primaryStage.setTitle("Hello World");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        //primaryStage.setFullScreen(true);
        //primaryStage.setFullScreenExitHint("");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
