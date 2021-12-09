package org.work.controller.user;

import org.work.domen.com.Com;
import org.work.domen.com.Coms;
import org.work.domen.com.factory.CommandFactory;
import org.work.domen.config.Config;
import org.work.domen.entity.User;
import org.work.domen.entity.UserStatus;
import org.work.domen.message.Message;
import org.work.launch.Launcher;
import org.work.launch.LauncherContext;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import static org.work.controller.ShowException.showErrorException;
import static org.work.domen.config.Config.SIGN_IN_PAGE;

public class UserMenuController {

    @FXML
    private Button btnUsersTable;
    @FXML
    private Button btnSegmentsTable;
    @FXML
    private Button btnCompaniesTable;
    @FXML
    private TextField textLogin;
    @FXML
    private TextField textName;
    @FXML
    private TextField textSurname;


    ///

    private User user = Launcher.getUser();


    public void initialize() {
        //
        this.textLogin.setText(user.getLogin());
        this.textLogin.setEditable(false);
        this.textName.setText(user.getName());
        this.textSurname.setText(user.getSurname());

        //
        if (!user.getUserStatus().equals(UserStatus.ADMIN)) {
            btnSegmentsTable.setVisible(false);
            btnUsersTable.setVisible(false);
        }
    }

    public void exit(ActionEvent actionEvent) {
        Launcher.changeScene(SIGN_IN_PAGE);
    }


    public void update(ActionEvent actionEvent) {
        final String login = textLogin.getText();
        final String name = textName.getText();
        final String surname = textSurname.getText();


        if (!login.equals("")
                && !name.equals("")
                && !surname.equals("")
                ) {


                Message request = new Message();

                user.setLogin(login);
                user.setName(name);
                user.setSurname(surname);


                //
                request.add("user", user);
                //
                Com command = CommandFactory.getInstance().createCommand(Coms.UPDATE_USER);
                Message response = command.execute(request);
                String exception = (String) response.getByKey("ex");
                if (exception != null) {
                    showErrorException(exception);
                }

        } else {
            showErrorException("Заполните все поля!");
        }
    }


    public void updatePassword(ActionEvent actionEvent) {
       Launcher.changeScene(Config.PASSWORD_PAGE);
    }

    public void showCompaniesTable(ActionEvent actionEvent) {
        Launcher.changeScene(Config.PROJECT_TABLE_PAGE);
    }

    ///////////////////////////////////////////////////////////////////////

    public void showUsersStable(ActionEvent actionEvent) {
        Launcher.changeScene(Config.USERS_TABLE_PAGE);
    }


    public void showSegmentsTable(ActionEvent actionEvent) {
       Launcher.changeScene(Config.SEGMENT_TABLE_PAGE);
    }

}
