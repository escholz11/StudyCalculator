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
import javafx.stage.Stage;
import sample.LanguageHandler;
import sample.SoundHandler;

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
public class ShowResultController implements Initializable {

    @FXML
    Label resultLabel;

    @FXML
    Label studyCourseLabel;

    @FXML
    Label questionLabel;

    @FXML
    Label question1Label;

    @FXML
    Label question2Label;

    @FXML
    Label question3Label;

    @FXML
    Label correctAnswerLabel;

    @FXML
    Label yourAnswerLabel;

    @FXML
    Button forwardButton;

    @FXML
    Button abortButton;

    @FXML
    Button againButton;


    String question, correctAnswer, yourAnswer;
    static int currentFxml = 1;

    SoundHandler soundHandler = new SoundHandler();
    ResultController resultController = new ResultController();
    StartApplicationController startApp = new StartApplicationController();

    @FXML
    private void abortButtonAction(ActionEvent event) {

        if(soundHandler.soundStatus() == true) {
            soundHandler.playClickSound();
        }

        eraseAll();
        LanguageHandler languageHandler = new LanguageHandler();
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Scene scene = stage.getScene();

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

    @FXML
    private void forwardButtonAction(ActionEvent event) {

        if (resultController.getCorrectAnswerList().size() == ResultController.Counter1) {
            if (soundHandler.soundStatus() == true) {
                soundHandler.playClickSound();
            }
            eraseAll();
            showStartPage(event);

        } else {
            if (soundHandler.soundStatus() == true) {
                soundHandler.playClickSound();
            }

            question = (String) resultController.getQuestionList().get(ResultController.Counter0);
            ResultController.Counter0 += 5;
            correctAnswer = (String) resultController.getCorrectAnswerList().get(ResultController.Counter1);
            ResultController.Counter1++;
            yourAnswer = (String) resultController.getYourAnswerList().get(ResultController.Counter2);
            ResultController.Counter2++;

            LanguageHandler languageHandler = new LanguageHandler();
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            Scene scene = stage.getScene();

            FXMLLoader fxmlLoader = new FXMLLoader();
            if (question.length() >= 50 && question.length() < 100) {
                currentFxml = 2;
                fxmlLoader.setLocation(getClass().getResource("/fxml/showResult02.fxml"));
            } else if (question.length() > 100) {
                currentFxml = 3;
                fxmlLoader.setLocation(getClass().getResource("/fxml/showResult03.fxml"));
            } else {
                currentFxml = 1;
                fxmlLoader.setLocation(getClass().getResource("/fxml/showResult01.fxml"));
            }
            fxmlLoader.setResources(ResourceBundle.getBundle("RessourceBundle.MyBundle", languageHandler.getLanguage()));
            Parent root = null;
            try {
                root = (Parent) fxmlLoader.load();
                ShowResultController showResultController = (ShowResultController) fxmlLoader.getController();
                showResultController.studyCourseLabel.setText(startApp.getSelectedStudy());
                showResultController.resultLabel.setAlignment(Pos.CENTER);
                showResultController.correctAnswerLabel.setText(correctAnswer);
                showResultController.yourAnswerLabel.setText(yourAnswer);


                if (currentFxml == 1) {
                    showResultController.questionLabel.setText(question);
                }

                if (currentFxml == 2) {
                    showResultController.question1Label.setText(getFirstStringForLabel(question));
                    showResultController.question2Label.setText(getSecondStringForLabel(question));
                }

                if (currentFxml == 3) {
                    showResultController.question1Label.setText(getFirstStringForLabel(question));
                    showResultController.question2Label.setText(getSecondAndThirdStringForLabel(question));
                    showResultController.question3Label.setText(getThirdStringForLabel(question));
                }

            } catch (IOException e) {
                System.out.println("Cant load " + currentFxml + " file");
            }
            scene.setRoot(root);
        }

    }

    private String getFirstStringForLabel(String question) {
        int lastEmptyCharacter = 0;
        for(int i = 0; i < 50; i++) {
            if(question.charAt(i) == ' ') {
                lastEmptyCharacter = i;
            }
        }
        question = question.substring(0, lastEmptyCharacter);
        return question;
    }

    private String getSecondStringForLabel(String question) {
        int lastEmptyCharacterLine1 = 0;
        for(int i = 0; i < 50; i++) {
            if(question.charAt(i) == ' ') {
                lastEmptyCharacterLine1 = i;
            }
        }
        question = question.substring((lastEmptyCharacterLine1 + 1), question.length());
        return question;
    }

    private String getSecondAndThirdStringForLabel(String question) {
        int lastEmptyCharacterLine1 = 0;
        int lastEmptyCharacter = 0;
        for(int i = 0; i < 50; i++) {
            if(question.charAt(i) == ' ') {
                lastEmptyCharacterLine1 = i;
            }
        }

        for(int i = 0; i < 100; i++) {
            if(question.charAt(i) == ' ' && i > 50) {
                if(i > 50 ){
                    lastEmptyCharacter = i;
                }
            }
        }
        question = question.substring((lastEmptyCharacterLine1 + 1), lastEmptyCharacter);
        return question;
    }

    private String getThirdStringForLabel(String question) {
        int lastEmptyCharacter = 0;
        for(int i = 0; i < 100; i++) {
            if(question.charAt(i) == ' ') {
                lastEmptyCharacter = i;
            }
        }
        question = question.substring((lastEmptyCharacter + 1), question.length());
        return question;
    }

    private void showStartPage(ActionEvent event) {
        if(soundHandler.soundStatus() == true) {
            soundHandler.playClickSound();
        }
        LanguageHandler languageHandler = new LanguageHandler();
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Scene scene = stage.getScene();

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

    @FXML
    private void againButtonAction(ActionEvent event) {

        if(soundHandler.soundStatus() == true) {
            soundHandler.playClickSound();
        }
        eraseAll();
        LanguageHandler languageHandler = new LanguageHandler();
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Scene scene = stage.getScene();

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

    private void eraseAll() {
        AllQuestionsController.correctAnswers = 0;
        StartApplicationController.Counter = 0;
        AllQuestionsController allquestions = new AllQuestionsController();
        allquestions.getAnswerList().clear();
        ResultController.Counter0 = 0;
        ResultController.Counter1 = 0;
        ResultController.Counter2 = 0;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setMouseOverEvents();
    }

    public void setMouseOverEvents() {
        forwardButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                forwardButton.setStyle("-fx-background-color: #9C9C9C;");
            }
        });

        forwardButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                forwardButton.setStyle("-fx-background-color: #8B8B7A;");
            }
        });

        abortButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                abortButton.setStyle("-fx-background-color: #9C9C9C;");
            }
        });

        abortButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                abortButton.setStyle("-fx-background-color: #8B8B7A;");
            }
        });

        againButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                againButton.setStyle("-fx-background-color: #9C9C9C;");
            }
        });

        againButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                againButton.setStyle("-fx-background-color: #8B8B7A;");
            }
        });
    }
}
