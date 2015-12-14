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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.CSVHandler;
import sample.LanguageHandler;
import sample.SoundHandler;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
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
public class AllQuestionsController implements Initializable {

    @FXML
    Label questionLabel;

    @FXML
    Label question1Label;

    @FXML
    Label question2Label;

    @FXML
    Label question3Label;

    @FXML
    CheckBox answer1;

    @FXML
    CheckBox answer2;

    @FXML
    CheckBox answer3;

    @FXML
    CheckBox answer4;

    @FXML
    Button abortButton;

    @FXML
    Button forwardButton;

    public static int correctAnswers = 0;
    Object selectedAnswer;
    String selectedStudy, question, setanswer1, setanswer2, setanswer3, setanswer4;
    private Stage requestStage;

    private static LinkedList answerList = new LinkedList();
    private static LinkedList solutionList = new LinkedList();
    boolean correctSelection;
    boolean splitString = false;
    static int currentFxml = 1;

    public static LinkedList getAnswerList() {
        return answerList;
    }

    SoundHandler soundHandler = new SoundHandler();

    @FXML
    private void abortButtonAction(ActionEvent event) {

        if(soundHandler.soundStatus() == true) {
            soundHandler.playClickSound();
        }

        correctAnswers = 0;
        StartApplicationController.Counter = 0;
        answerList.clear();

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
        StartApplicationController.Counter = 0;
        correctAnswers = 0;
        answerList.clear();
    }

    @FXML
    private void forwardButtonAction(ActionEvent event) {
        if (soundHandler.soundStatus() == true) {
            soundHandler.playClickSound();
        }
        checkSelection();

        if (correctSelection == true) {

            saveUserSelection();

            answer1.setSelected(false);
            answer2.setSelected(false);
            answer3.setSelected(false);
            answer4.setSelected(false);

            StartApplicationController startApp = new StartApplicationController();
            selectedStudy = startApp.getSelectedStudy();

            if (startApp.getList().size() == StartApplicationController.Counter) {
                boolean positiveResult = evaluateResult(calculateResult());
                showResultStage(event, positiveResult, correctAnswers);

            } else {

                if (soundHandler.soundStatus() == true) {
                    soundHandler.playClickSound();
                }

                question = (String) startApp.getList().get(StartApplicationController.Counter);
                StartApplicationController.Counter++;
                setanswer1 = (String) startApp.getList().get(StartApplicationController.Counter);
                StartApplicationController.Counter++;
                setanswer2 = (String) startApp.getList().get(StartApplicationController.Counter);
                StartApplicationController.Counter++;
                setanswer3 = (String) startApp.getList().get(StartApplicationController.Counter);
                StartApplicationController.Counter++;
                setanswer4 = (String) startApp.getList().get(StartApplicationController.Counter);
                StartApplicationController.Counter++;

                LanguageHandler languageHandler = new LanguageHandler();
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                Scene scene = stage.getScene();

                FXMLLoader fxmlLoader = new FXMLLoader();
                if (question.length() >= 50 && question.length() < 100) {
                    currentFxml = 2;
                    fxmlLoader.setLocation(getClass().getResource("/fxml/allquestions02.fxml"));
                } else if (question.length() > 100) {
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

                    if (currentFxml == 1) {
                        allQuestions.questionLabel.setText(question);
                        allQuestions.questionLabel.setAlignment(Pos.CENTER);
                    }

                    if (currentFxml == 2) {
                        allQuestions.question1Label.setText(getFirstStringForLabel(question));
                        allQuestions.question2Label.setText(getSecondStringForLabel(question));
                        allQuestions.question1Label.setAlignment(Pos.CENTER);
                        allQuestions.question2Label.setAlignment(Pos.CENTER);
                    }

                    if (currentFxml == 3) {
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
    }

    private int calculateResult() {
        StartApplicationController startApp = new StartApplicationController();
        selectedStudy = startApp.getSelectedStudy();
        CSVHandler csvhandler = new CSVHandler();
        solutionList = (LinkedList) csvhandler.readFile(csvhandler.csvSolutionSelector(selectedStudy));
        for (int i = 0; i < solutionList.size(); i++) {
            if (answerList.get(i).equals(solutionList.get(i))) {
                correctAnswers++;
            }
        }
        return correctAnswers;
    }

    private boolean evaluateResult(int answers) {
        if (solutionList.size() >= 10) {
            if (answers >= ((solutionList.size() / 2) - 1)) {
                return true;
            } else {
                return false;
            }
        } else {
            if (answers >= (solutionList.size() / 2)) {
                return true;
            } else {
                return false;
            }
        }
    }

    private void showResultStage(ActionEvent event, boolean positiveResult, int correctAnswers) {
        if(soundHandler.soundStatus() == true) {
            soundHandler.playClickSound();
        }
        LanguageHandler languageHandler = new LanguageHandler();
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Scene scene = stage.getScene();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/result.fxml"));
        fxmlLoader.setResources(ResourceBundle.getBundle("RessourceBundle.MyBundle", languageHandler.getLanguage()));
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
            ResultController resultController = (ResultController) fxmlLoader.getController();
            resultController.result1Label.setAlignment(Pos.CENTER);
            resultController.result3Label.setAlignment(Pos.CENTER);
            resultController.result4Label.setAlignment(Pos.CENTER);
            if (languageHandler.getLanguage().toString().equals("de")) {
                if (correctAnswers == 1) {
                    resultController.result3Label.setText("Sie haben " + correctAnswers + " von " +  solutionList.size() + " Fragen richtig");
                } else {
                    resultController.result3Label.setText("Sie haben " + correctAnswers + " von " +  solutionList.size() + " Fragen richtig");
                }

                if (positiveResult == true) {
                    resultController.result4Label.setText("Der Studiengang ist zu empfehlen");
                } else {
                    resultController.result4Label.setText("Der Studiengang ist nicht zu empfehlen");
                }
            } else {
                if (correctAnswers == 1) {
                    resultController.result3Label.setText("You gave " + correctAnswers + " correct answer overall");
                } else {
                    resultController.result3Label.setText("You gave " + correctAnswers + " correct answers overall");
                }
                if (positiveResult == true) {
                    resultController.result4Label.setText("The Study Course is recommended");
                } else {
                    resultController.result4Label.setText("The Study Course is not recommended");
                }
            }
        } catch (IOException e) {
            System.out.println("Cant load file. Please verify path!");
        }
        scene.setRoot(root);
    }

    private void saveUserSelection() {
        if (answer1.isSelected()) {
            selectedAnswer = answer1.getText();
            answerList.add(selectedAnswer);
        }
        if (answer2.isSelected()) {
            selectedAnswer = answer2.getText();
            answerList.add(selectedAnswer);
        }
        if (answer3.isSelected()) {
            selectedAnswer = answer3.getText();
            answerList.add(selectedAnswer);
        }
        if (answer4.isSelected()) {
            selectedAnswer = answer4.getText();
            answerList.add(selectedAnswer);
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

    private boolean checkSelection() {
        correctSelection = true;
        if ((!answer1.isSelected() && !answer2.isSelected() && !answer3.isSelected() && !answer4.isSelected())) {
            correctSelection = false;
            requestStage = new Stage();
            requestStage.setTitle("Confirmed");
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
        }

        if ((answer1.isSelected() && answer2.isSelected()) || (answer1.isSelected() && answer3.isSelected()) || (answer1.isSelected() && answer4.isSelected()) || (answer2.isSelected() && answer3.isSelected()) || (answer3.isSelected() && answer4.isSelected()) || (answer2.isSelected() && answer4.isSelected()) || (answer1.isSelected() && answer2.isSelected() && answer3.isSelected()) || (answer1.isSelected() && answer2.isSelected() && answer4.isSelected()) || (answer1.isSelected() && answer3.isSelected() && answer4.isSelected()) || (answer2.isSelected() && answer3.isSelected() && answer4.isSelected()) || (answer1.isSelected() && answer2.isSelected() && answer3.isSelected() && answer4.isSelected())) {
            correctSelection = false;
            requestStage = new Stage();
            requestStage.setTitle("Confirmed");
            requestStage.initModality(Modality.APPLICATION_MODAL);
            LanguageHandler languageHandler = new LanguageHandler();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/fxml/chooseOneOption.fxml"));
            fxmlLoader.setResources(ResourceBundle.getBundle("RessourceBundle.MyBundle", languageHandler.getLanguage()));
            Parent root = null;
            try {
                root = (Parent) fxmlLoader.load();
                PopUpController popUpController = (PopUpController) fxmlLoader.getController();
                popUpController.oneOptionLabel.setAlignment(Pos.CENTER);
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
        return correctSelection;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setMouseOverEvents();
    }

    public void setMouseOverEvents() {
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
