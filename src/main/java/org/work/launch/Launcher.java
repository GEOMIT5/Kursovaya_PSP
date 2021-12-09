package org.work.launch;

import org.work.domen.entity.User;
import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static org.work.domen.config.Config.SIGN_IN_PAGE;

public class Launcher extends Application {

    private static Stage primaryStage;
    private static FXMLLoader fxmlLoader = new FXMLLoader();

    private static User user;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        Launcher.user = user;
    }


    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    @FXML
    public static void changeScene(String nextStagePath, Object... params) {
        try {
            URL url = new File(nextStagePath).toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Scene second = new Scene(root);
            primaryStage.setTitle("");
            primaryStage.setScene(second);
            fxmlLoader.getController();
        } catch (IOException ignore) {
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Launcher.primaryStage = primaryStage;
        ///
        URL url = new File(SIGN_IN_PAGE).toURI().toURL();
        Parent root = FXMLLoader.load(url);
        primaryStage.setTitle("");
        ///
        primaryStage.setScene(new Scene(root, 700, 520));
        primaryStage.setOnCloseRequest(Event::consume);
        primaryStage.show();
    }
    public static void exit() {
        System.exit(0);
    }


    public static void main(String[] args) {
        launch(args);
    }

}
