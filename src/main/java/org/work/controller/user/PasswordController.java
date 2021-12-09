package org.work.controller.user;

import org.work.controller.ShowException;
import org.work.domen.com.Com;
import org.work.domen.com.Coms;
import org.work.domen.com.factory.CommandFactory;
import org.work.domen.config.Config;
import org.work.domen.entity.User;
import org.work.domen.entity.UserStatus;
import org.work.domen.message.Message;
import org.work.launch.Launcher;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;

import static org.work.controller.ShowException.showErrorException;
import static org.work.controller.ShowException.showMessageException;

public class PasswordController {

    @FXML
    private PasswordField textCurPass;
    @FXML
    private PasswordField textNewPass;
    @FXML
    private PasswordField textNewConfPass;

    private User user = Launcher.getUser();


    public void initialize() {

    }


    public void update(ActionEvent actionEvent) {

        String curPass = textCurPass.getText();
        String newPass = textNewPass.getText();
        String newPassConf = textNewConfPass.getText();

        if (!curPass.equals("")
                && !newPass.equals("")
                && !newPassConf.equals("")) {

            if (newPass.equals(newPassConf)) {


                Message message = new Message();

                message.add("userId", user.getId());
                message.add("curPass", curPass);
                message.add("newPass", newPass);
                message.add("newPassConf", newPassConf);

                Com command = CommandFactory.getInstance().createCommand(Coms.UPDATE_USER_PASSWORD);

                Message response = command.execute(message);
                String exception = (String) response.getByKey("ex");
                if (exception != null) {
                    showErrorException(exception);
                    clearFields();
                } else {
                    showMessageException("Пароль сменен!");
                    clearFields();
                }
            } else {
                ShowException.showErrorException("новый пароль не совпал с подтверждённым!");
                clearFields();
            }

        } else {
            ShowException.showErrorException("Заполните все поля");
        }

    }

    private void clearFields() {
        this.textCurPass.setText("");
        this.textNewPass.setText("");
        this.textNewConfPass.setText("");
    }

    public void back(ActionEvent actionEvent) {
        Launcher.changeScene(Config.USER_MENU_PAGE);
    }



}
