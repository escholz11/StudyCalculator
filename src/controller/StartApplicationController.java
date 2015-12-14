package controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.CSVHandler;
import sample.LanguageHandler;
import sample.SoundHandler;

import java.io.IOException;
import java.net.URL;
import java.util.List;
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
public class StartApplicationController implements Initializable {

    @FXML
    Label topLabel;

    @FXML
    Label midLabel;

    @FXML
    Label selectLabel;

    @FXML
    Button backButton;

    @FXML
    Button forwardButton;

    @FXML
    private ComboBox studyCourseDropDown;

    String question, setanswer1, setanswer2, setanswer3, setanswer4, csvPath;

    private static String selectedStudy = "";
    static int Counter = 0;
    static int currentFxml = 1;

    private static List list;

    private Stage requestStage;

    public List getList() {
        return list;
    }

    public String getSelectedStudy() {
        return this.selectedStudy;
    }

    ObservableList<String> studyList = FXCollections.observableArrayList(
            "Telematik", "Maschinenbau", "Wirtschaftsingenieurwesen", "Logistik", "Bioinformatik"
    );

    //SoundHandler gameSoundHandler = new SoundHandler();
    SoundHandler clickSoundHandler = new SoundHandler();

    @FXML
    private void backButtonAction(ActionEvent event) {

        if(clickSoundHandler.soundStatus() == true) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    clickSoundHandler.playClickSound();
                    //StartPageController.gameSoundHandler.stopGameSound();
                }
            });
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
    private void forwardButtonAction(ActionEvent event) {
        if(clickSoundHandler.soundStatus() == true) {
            clickSoundHandler.playClickSound();
        }
        selectedStudy = (String) studyCourseDropDown.getValue();
        if(selectedStudy == null) {
            requestStage = new Stage();
            requestStage.setTitle("Choose Option");
            requestStage.initModality(Modality.APPLICATION_MODAL);
            LanguageHandler languageHandler = new LanguageHandler();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/fxml/chooseOption.fxml"));
            fxmlLoader.setResources(ResourceBundle.getBundle("RessourceBundle.MyBundle", languageHandler.getLanguage()));
            Parent root = null;
            try {
                root = (Parent) fxmlLoader.load();
                PopUpController popUpController = (PopUpController) fxmlLoader.getController();
                popUpController.optionLabel.setAlignment(Pos.CENTER);
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
        } else {
            CSVHandler csvhandler = new CSVHandler();
            csvPath = csvhandler.csvSelector(selectedStudy);
            list = csvhandler.readFile(csvPath);

            question = (String) list.get(Counter);
            Counter++;
            setanswer1 = (String) list.get(Counter);
            Counter++;
            setanswer2 = (String) list.get(Counter);
            Counter++;
            setanswer3 = (String) list.get(Counter);
            Counter++;
            setanswer4 = (String) list.get(Counter);
            Counter++;

            LanguageHandler languageHandler = new LanguageHandler();
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            Scene scene = stage.getScene();

            FXMLLoader fxmlLoader = new FXMLLoader();
            if(question.length() >= 50 && question.length() < 100) {
                currentFxml = 2;
                fxmlLoader.setLocation(getClass().getResource("/fxml/allquestions02.fxml"));
            } else if(question.length() > 100) {
                currentFxml = 3;
                fxmlLoader.setLocation(getClass().getResource("/fxml/allquestions03.fxml"));
            } else {
                currentFxml = 1;
                fxmlLoader.setLocation(getClass().getResource("/fxml/allquestions01.fxml"));
            }
            fxmlLoader.setResources(ResourceBundle.getBundle("RessourceBundle.MyBundle", languageHandler.getLanguage()));
            Parent root = null;
            try {
                root = (Parent) fxmlLoader.load();
                AllQuestionsController allQuestions = (AllQuestionsController) fxmlLoader.getController();

                if(currentFxml == 1) {
                    allQuestions.questionLabel.setText(question);
                    allQuestions.questionLabel.setAlignment(Pos.CENTER);
                }

                if(currentFxml == 2) {
                    allQuestions.question1Label.setText(getFirstStringForLabel(question));
                    allQuestions.question2Label.setText(getSecondStringForLabel(question));
                    allQuestions.question1Label.setAlignment(Pos.CENTER);
                    allQuestions.question2Label.setAlignment(Pos.CENTER);
                }

                if(currentFxml == 3) {
                    allQuestions.question1Label.setText(getFirstStringForLabel(question));
                    allQuestions.question2Label.setText(getSecondAndThirdStringForLabel(question));
                    allQuestions.question3Label.setText(getThirdStringForLabel(question));
                    allQuestions.question1Label.setAlignment(Pos.CENTER);
                    allQuestions.question2Label.setAlignment(Pos.CENTER);
                    allQuestions.question3Label.setAlignment(Pos.CENTER);
                }

                allQuestions.answer1.setText(setanswer1);
                allQuestions.answer2.setText(setanswer2);
                allQuestions.answer3.setText(setanswer3);
                allQuestions.answer4.setText(setanswer4);
            } catch (IOException e) {
                System.out.println("Cant load file. Please verify path!");
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

    @FXML
    private void studyCourseAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        studyCourseDropDown.setItems(studyList);
        topLabel.setAlignment(Pos.CENTER);
        midLabel.setAlignment(Pos.CENTER);
        selectLabel.setAlignment(Pos.CENTER);
        setMouseOverEvents();
    }

    public void setMouseOverEvents() {
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

    }
}
