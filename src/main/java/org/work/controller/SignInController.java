package org.work.controller;

import org.work.domen.com.Com;
import org.work.domen.com.Coms;
import org.work.domen.com.factory.CommandFactory;
import org.work.domen.connector.Connector;
import org.work.domen.message.Message;
import org.work.launch.Launcher;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

import static org.work.controller.ShowException.showErrorException;
import static org.work.domen.config.Config.SIGN_IN_PAGE;
import static org.work.domen.config.Config.SIGN_UP_PAGE;

public class SignInController {

    @FXML
    private TextField textPort;
    @FXML
    private TextField textLogin;
    @FXML
    private Button btnSignUp;

    @FXML
    private PasswordField textPassword;

    public void initialize() throws IOException {
        int port = Connector.getPort();
        if (port != 0) {
            btnSignUp.setVisible(true);
            //
            this.textPort.setText(String.valueOf(port));
        } else {
            btnSignUp.setVisible(false);
        }
        ///
        textPort.setPromptText("Введите порт");
        textPort.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    textPort.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        textLogin.setPromptText("Введите логин");
        textPassword.setPromptText("Введите пароль");
    }

    public void SignIn(ActionEvent actionEvent) {
        final String login = textLogin.getText();
        final String password = textPassword.getText();
        final String portString = textPort.getText();
        if (!Connector.getInstance().isConnectionCreated() ||
                (!portString.equals("") && Integer.parseInt(portString) != Connector.getPort())) {
            if (portString.length() > 5 || portString.equals("")) {
                showErrorException("Для установки порта введите число от 1 до 65535");
                textLogin.clear();
                textPassword.clear();
            } else {
                int port = Integer.parseInt(portString);
                if (port <= 0 || port > 65535) {
                    textLogin.clear();
                    textPassword.clear();
                    showErrorException("Введите число от 1 до 65535");
                } else {
                    try {
                        makeConnection(port);
                        //
                        btnSignUp.setVisible(true);
                    } catch (Exception ex) {
                        btnSignUp.setVisible(false);
                        //
                        textPort.clear();
                        textLogin.clear();
                        textPassword.clear();
                        showErrorException("Не удалось подключиться к серверу! попробуйте другой порт");
                    }
                }
            }
        } else {
            if (login.equals("") || password.equals("")) {
                showErrorException("Все поля должны быть заполнены!");
                textLogin.clear();
                textPassword.clear();
            } else {

                Com command = CommandFactory.getInstance().createCommand(Coms.SIGN_IN);
                Message message = new Message();
                message.add("login", login);
                message.add("password", password);
                Message response = command.execute(message);
                String exception = (String) response.getByKey("ex");

                if (exception != null) {
                    showErrorException(exception);
                    this.textPassword.setText("");
                }

            }
        }
    }

    private void makeConnection(int port) throws Exception {
        Connector.setPort(port);
        Connector.getInstance().doCreateConnection();
        ShowException.showMessageException("Подключение состоялось!");
    }

    public void Exit(ActionEvent actionEvent) {
        Launcher.exit();
    }


    public void SignUp(ActionEvent actionEvent) {
        Launcher.changeScene(SIGN_UP_PAGE);
    }


}
