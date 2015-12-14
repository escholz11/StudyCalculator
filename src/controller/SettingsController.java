package controller;

import javafx.application.Platform;
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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;


/**
 * Content: Controller Class
 * Created by Enrico Scholz on 11.04.2014 at 15:11
 *
 * @author Enrico Scholz
 * @version 1.1
 * @since 11.04.2014
 */
public class SettingsController implements Initializable {

    @FXML
    Label topLabel;

    @FXML
    Button germanButton;

    @FXML
    Button englishButton;

    @FXML
    Button onButton;

    @FXML
    Button offButton;

    @FXML
    Button backButton;

    private Stage requestStage;

    SoundHandler soundHandler = new SoundHandler();
    @FXML
    private void englishButtonAction(ActionEvent event){

        if(soundHandler.soundStatus() == true) {
            soundHandler.playClickSound();
        }
        LanguageHandler languageHandler = new LanguageHandler();
        languageHandler.changeLanguage("en");
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Scene scene = stage.getScene();

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
    private void germanButtonAction(ActionEvent event){
        if(soundHandler.soundStatus() == true) {
            soundHandler.playClickSound();
        }
        LanguageHandler languageHandler = new LanguageHandler();
        languageHandler.changeLanguage("de");
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Scene scene = stage.getScene();

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
    private void onButtonAction(ActionEvent event){
        soundHandler.activateSound();
        soundHandler.playClickSound();
        SoundHandler.gameSoundPlayed = false;
        requestStage = new Stage();
        requestStage.setTitle("Confirmed");
        requestStage.initModality(Modality.APPLICATION_MODAL);
        LanguageHandler languageHandler = new LanguageHandler();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/confirmed.fxml"));
        fxmlLoader.setResources(ResourceBundle.getBundle("RessourceBundle.MyBundle", languageHandler.getLanguage()));
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
            PopUpController popUpController = (PopUpController) fxmlLoader.getController();
            popUpController.confirmedLabel.setAlignment(Pos.CENTER);
            Scene scene = new Scene(root, 300, 50);
            requestStage.setScene(scene);
            requestStage.show();

            Platform.runLater(() -> {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                requestStage.close();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void offButtonAction(ActionEvent event){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                soundHandler.playClickSound();
                StartPageController.gameSoundHandler.stopGameSound();
            }
        });
        soundHandler.deactivateSound();

        requestStage = new Stage();
        requestStage.setTitle("Confirmed");
        requestStage.initModality(Modality.APPLICATION_MODAL);
        LanguageHandler languageHandler = new LanguageHandler();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/confirmed.fxml"));
        fxmlLoader.setResources(ResourceBundle.getBundle("RessourceBundle.MyBundle", languageHandler.getLanguage()));
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
            PopUpController popUpController = (PopUpController) fxmlLoader.getController();
            popUpController.confirmedLabel.setAlignment(Pos.CENTER);
            Scene scene = new Scene(root, 300, 50);
            requestStage.setScene(scene);
            requestStage.show();

            Platform.runLater(() -> {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                requestStage.close();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void backButtonAction(ActionEvent event){
        if(soundHandler.soundStatus() == true) {
            soundHandler.playClickSound();
        }
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Scene scene = stage.getScene();

        LanguageHandler languageHandler = new LanguageHandler();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/startpage.fxml"));
        fxmlLoader.setResources(ResourceBundle.getBundle("RessourceBundle.MyBundle", languageHandler.getLanguage()));
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            System.out.println("Cant load file. Please verify path!");
        }
        scene.setRoot(root);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        topLabel.setAlignment(Pos.CENTER);
        setMouseOverEvents();
    }

    public void setMouseOverEvents() {
        germanButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                germanButton.setStyle("-fx-background-color: #9C9C9C;");
            }
        });

        germanButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                germanButton.setStyle("-fx-background-color: #8B8B7A;");
            }
        });

        englishButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                englishButton.setStyle("-fx-background-color: #9C9C9C;");
            }
        });

        englishButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                englishButton.setStyle("-fx-background-color: #8B8B7A;");
            }
        });

        onButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                onButton.setStyle("-fx-background-color: #9C9C9C;");
            }
        });

        onButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                onButton.setStyle("-fx-background-color: #8B8B7A;");
            }
        });

        offButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                offButton.setStyle("-fx-background-color: #9C9C9C;");
            }
        });

        offButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                offButton.setStyle("-fx-background-color: #8B8B7A;");
            }
        });

        backButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                backButton.setStyle("-fx-background-color: #9C9C9C;");
            }
        });

        backButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                backButton.setStyle("-fx-background-color: #8B8B7A;");
            }
        });
    }
}
