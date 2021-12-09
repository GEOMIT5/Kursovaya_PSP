package org.work.launch;

import org.work.controller.BaseController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;


public class LauncherContext {

    public LauncherContext() {

    }

    private BaseController dialogWindowController;
    private Stage currentStage;
    private Parent fxmlEdit;
    private final FXMLLoader fxmlLoader = new FXMLLoader();

    public BaseController getDialogController(BaseController dialogWindow, String page) {
        this.dialogWindowController = dialogWindow;
        initLoader(page);
        return this.dialogWindowController;
    }


    private void initLoader(String page) {
        try {
            URL url = new File(page).toURI().toURL();
            fxmlLoader.setLocation(url);
            fxmlEdit = fxmlLoader.load();
            dialogWindowController = fxmlLoader.getController();
        } catch (IOException ignore) {
        }
    }

    public void showDialog() {
        if (currentStage == null) {
            currentStage = new Stage();
            currentStage.setTitle("");
            currentStage.setScene(new Scene(fxmlEdit));
            currentStage.initModality(Modality.WINDOW_MODAL);
            currentStage.initOwner(Launcher.getPrimaryStage());
        }
        currentStage.showAndWait();
    }


}
