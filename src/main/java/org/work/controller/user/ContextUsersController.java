package org.work.controller.user;

import org.work.controller.BaseController;
import org.work.controller.ShowException;
import org.work.domen.entity.User;
import org.work.domen.entity.UserStatus;
import org.work.domen.utilit.builder.impl.UserBuilderImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.work.controller.ShowException.showErrorException;

public class ContextUsersController implements BaseController {

    @FXML
    private TextField textLogin;
    @FXML
    private TextField textPassword;
    @FXML
    private TextField textName;
    @FXML
    private TextField textSurname;
    @FXML

    private ChoiceBox<UserStatus> choiceStatus;
    private static final ObservableList<UserStatus> STATUSES = FXCollections.observableList(
            Arrays.stream(UserStatus.values()).collect(Collectors.toList()));

    private User user;


    public void initialize() {

        choiceStatus.setVisible(true);

        choiceStatus.setItems(STATUSES);
    }

    public void setNullEntity() {
        this.user = null;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        if (user == null) {
            return;
        }
        textLogin.setEditable(false);
        this.user = user;
        //
        textLogin.setText(user.getLogin());
        textPassword.setText(user.getPassword());
        textName.setText(user.getName());
        textSurname.setText(user.getSurname());

        //
        this.choiceStatus.setValue(
                STATUSES.stream().filter(e -> e.getId() == user.getUserStatus().getId())
                        .findFirst()
                        .get()
        );
    }

    public void actionSave(ActionEvent actionEvent) {
        String login = textLogin.getText();
        String password = textPassword.getText();
        String name = textName.getText();
        String surname = textSurname.getText();



        if (!(
                password.equals("")
                        && name.equals("")
                        && surname.equals("")
                       )) {

            //

                if (!choiceStatus.getSelectionModel().isEmpty()) {

                    if (this.user != null) {
                        this.user.setLogin(login);
                        this.user.setPassword(password);
                        this.user.setName(name);
                        this.user.setSurname(surname);
                        this.user.setUserStatus(choiceStatus.getValue());
                    } else {
                        this.user = new UserBuilderImpl()
                                .withLogin(login)
                                .withPassword(password)
                                .withName(name)
                                .withSurname(surname)
                                .withUserStatus(choiceStatus.getValue())
                                .build();
                    }
                    //
                    actionClose(actionEvent);
                } else {
                    showErrorException("Выберите роль!");
                }

        } else {
            ShowException.showErrorException("Заполните все поля");
        }

    }


    public void actionClose(ActionEvent actionEvent) {
        makeClearFields();

        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }

    private void makeClearFields() {
        textLogin.setEditable(true);
        textLogin.clear();
        textPassword.clear();
        textName.clear();
        textSurname.clear();

    }


}
