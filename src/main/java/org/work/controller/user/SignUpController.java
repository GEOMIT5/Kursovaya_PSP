package org.work.controller.user;

import org.work.domen.com.Com;
import org.work.domen.com.Coms;
import org.work.domen.com.factory.CommandFactory;
import org.work.domen.entity.UserStatus;
import org.work.domen.message.Message;
import org.work.launch.Launcher;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import static org.work.controller.ShowException.showErrorException;
import static org.work.domen.config.Config.SIGN_IN_PAGE;

public class SignUpController {

    @FXML
    private TextField textLogin;

    @FXML
    private TextField textPassword;

    @FXML
    private TextField textName;

    @FXML
    private TextField textSurname;



    public void initialize() {
        textLogin.setPromptText("введите логин");
        textPassword.setPromptText("введите пароль");
        textName.setPromptText("введите имя");
        textSurname.setPromptText("введите фамилию");

    }

    public void signUp(ActionEvent actionEvent) {
        final String login = textLogin.getText();
        final String password = textPassword.getText();
        final String name = textName.getText();
        final String surname = textSurname.getText();


        if (!(
                login.equals("")
                        && password.equals("")
                        && name.equals("")
                        && surname.equals("")
                        )) {


                Message message = new Message();


                message.add("login", login);
                message.add("password", password);
                message.add("name", name);
                message.add("surname", surname);
                message.add("userStatus", UserStatus.USER);

                Com command = CommandFactory.getInstance().createCommand(Coms.SIGN_UP);
                Message response = command.execute(message);
                String exception = (String) response.getByKey("ex");
                if (exception != null) {
                    showErrorException(exception);
                }

        }else{
            showErrorException("Заполните все поля");
        }
    }


    public void goBack(ActionEvent actionEvent) {
        Launcher.changeScene(SIGN_IN_PAGE);
    }

}
