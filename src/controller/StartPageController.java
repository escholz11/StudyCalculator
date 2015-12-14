package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.LanguageHandler;
import sample.SoundHandler;
import sample.StageManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Content: Controller Class
 * Created by Enrico Scholz on 11.04.2014 at 15:11
 *
 * @author Enrico Scholz
 * @version 1.1
 * @since 11.04.2014
 */
public class StartPageController implements Initializable {

    @FXML
    Label topLabel;

    @FXML
    Label midLabel;

    @FXML
    Button startApplicationButton;

    @FXML
    Button creditsButton;

    @FXML
    Button settingsButton;


    private static Stage creditStage;
    SoundHandler clickSoundHandler = new SoundHandler();
    static SoundHandler gameSoundHandler = new SoundHandler();

    @FXML
    private void settingsButtonAction(ActionEvent event){

        if(clickSoundHandler.soundStatus() == true) {
            clickSoundHandler.playClickSound();
        }
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Scene scene = stage.getScene();

        LanguageHandler languageHandler = new LanguageHandler();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/settings.fxml"));
        fxmlLoader.setResources(ResourceBundle.getBundle("RessourceBundle.MyBundle", languageHandler.getLanguage()));
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            System.out.println("Cant load file. Please verify path!");
        }
        scene.setRoot(root);
    }

    @FXML
    private void startApplicationButtonAction(ActionEvent event){

        if(clickSoundHandler.soundStatus() == true) {
            clickSoundHandler.playClickSound();
            //gameSoundHandler.playGameSound();
        }

        LanguageHandler languageHandler = new LanguageHandler();
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            Scene scene = stage.getScene();

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/fxml/startApplication.fxml"));
            fxmlLoader.setResources(ResourceBundle.getBundle("RessourceBundle.MyBundle", languageHandler.getLanguage()));
            Parent root = null;
            try {
                root = (Parent) fxmlLoader.load();
            } catch (IOException e) {
                System.out.println("Cant load file. Please verify path!");
            }
            scene.setRoot(root);
    }

    @FXML
    private void creditsButtonAction(ActionEvent event) throws IOException, InterruptedException {

        if(clickSoundHandler.soundStatus() == true) {
            clickSoundHandler.playClickSound();
        }
        creditStage = new Stage();
        creditStage.setTitle("Credits");
        creditStage.initModality(Modality.APPLICATION_MODAL);
        LanguageHandler languageHandler = new LanguageHandler();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/credits.fxml"));
        fxmlLoader.setResources(ResourceBundle.getBundle("RessourceBundle.MyBundle", languageHandler.getLanguage()));
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root,700,500);
        creditStage.setScene(scene);
        creditStage.show();
        StageManager.getInstance().setPopupStage(creditStage);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(clickSoundHandler.soundStatus() == true) {
            gameSoundHandler.playGameSound();
        }

        topLabel.setAlignment(Pos.CENTER);
        midLabel.setAlignment(Pos.CENTER);
        setMouseOverEvents();
    }

    public void setMouseOverEvents() {
        startApplicationButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                startApplicationButton.setStyle("-fx-background-color: #9C9C9C;");
            }
        });

        startApplicationButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                startApplicationButton.setStyle("-fx-background-color: #8B8B7A;");
            }
        });

        creditsButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                creditsButton.setStyle("-fx-background-color: #9C9C9C;");
            }
        });

        creditsButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                creditsButton.setStyle("-fx-background-color: #8B8B7A;");
            }
        });

        settingsButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                settingsButton.setStyle("-fx-background-color: #9C9C9C;");
            }
        });

        settingsButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                settingsButton.setStyle("-fx-background-color: #8B8B7A;");
            }
        });
    }
}
