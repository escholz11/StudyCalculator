package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import sample.SoundHandler;
import sample.StageManager;

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
public class CreditsController implements Initializable {

    SoundHandler soundHandler = new SoundHandler();

    @FXML
    Button closeButton;

    @FXML
    private void closeButtonAction(ActionEvent event){
        if(soundHandler.soundStatus() == true) {
            soundHandler.playClickSound();
        }
        StageManager.getInstance().getPopupStage().close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setMouseOverEvents();
    }

    public void setMouseOverEvents() {
        closeButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                closeButton.setStyle("-fx-background-color: #9C9C9C;");
            }
        });

        closeButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                closeButton.setStyle("-fx-background-color: #8B8B7A;");
            }
        });
    }

}
