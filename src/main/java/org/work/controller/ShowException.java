package org.work.controller;

import javafx.scene.control.Alert;

public final class ShowException {

    private ShowException() {

    }


    public static void showErrorException(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.showAndWait();
    }


    public static void showMessageException(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.showAndWait();
    }


}
