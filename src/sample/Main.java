package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ResourceBundle;

/**
 * Project Name Study Calculator
 * This Class FILL IN CONTENT
 * Created by E.Scholz on 08.01.2015 12:33.
 */
public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        StageManager.getInstance().setPrimaryStage(primaryStage);
        LanguageHandler language = new LanguageHandler();
        if (System.getProperty("user.language").equals("de") ||
                System.getProperty("user.language").equals("en")) {
            language.changeLanguage(System.getProperty("user.language"));
        }
        else {
            language.changeLanguage("en");
        }
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/startpage.fxml"));

        fxmlLoader.setResources(ResourceBundle.getBundle("RessourceBundle.MyBundle", language.getLanguage()));
        fxmlLoader.load();
        Parent root = fxmlLoader.getRoot();

        primaryStage.setTitle("Study Calculator");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
