package sample;

import javafx.stage.Stage;

/**
 * Content: this class gives access to primary stage
 * Created by Enrico Scholz on 13.04.2014 at 17:12
 *
 * @author Enrico Scholz
 * @version 1.0
 * @since 13.04.2014
 */
public class StageManager {

    private static StageManager instance;
    private static Stage primaryStage;
    private static Stage popupStage;

    //private constructor, so you cant modify
    private StageManager() {
    }

    /**
     * method used to call functions from primarystage
     * @return singleton instance
     */
    public synchronized static StageManager getInstance() {
        if (instance == null) {
            instance = new StageManager();
        }
        return instance;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Stage getPopupStage() {
        return popupStage;
    }

    public void setPopupStage(Stage popupStage) {
        this.popupStage = popupStage;
    }
}

