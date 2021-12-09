package org.work.controller.user;

import org.work.controller.ShowException;
import org.work.domen.com.Com;
import org.work.domen.com.Coms;
import org.work.domen.com.factory.CommandFactory;
import org.work.domen.config.Config;
import org.work.domen.entity.User;
import org.work.domen.message.Message;
import org.work.launch.Launcher;
import org.work.launch.LauncherContext;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.stream.Collectors;

import static org.work.controller.SendInfoServer.sendInfoServer;
import static org.work.domen.config.Config.USER_MENU_PAGE;

public class UserTableController {

    @FXML
    private TableView<User> table;
    @FXML
    private TableColumn<User, String> columnLogin;
    @FXML
    private TableColumn<User, String> columnName;
    @FXML
    private TableColumn<User, String> columnSurname;
    @FXML
    private TableColumn<User, String> columnStatus;
    //////////////
    @FXML
    private Button btnAddNew;
    @FXML
    private Button btnChange;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnFind;
    @FXML
    private TextField textFind;

    private ContextUsersController contextUsersController;
    private LauncherContext launcherContext = new LauncherContext();

    public void initialize() {
        contextUsersController = (ContextUsersController) launcherContext
                .getDialogController(contextUsersController, Config.CONTEXT_USERS_PAGE);
        prepareAndSetDataToTable();
        initListeners();
        textFind.setPromptText("Введите фамилию");
    }

    private void prepareAndSetDataToTable() {
        initColumns();
        initList();
        table.refresh();
    }

    private void initListeners() {
        table.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                viewChangePatient(table.getSelectionModel().getSelectedItem());
            }
        });

    }

    private void initList() {
        Com command = CommandFactory.getInstance().createCommand(Coms.GET_ALL_USERS);
        Message message = new Message();
        Message response = command.execute(message);
        ObservableList<User> users = (ObservableList<User>) response.getByKey("users");
        table.setItems(users);
    }


    private void initColumns() {
        columnLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        columnStatus.setCellValueFactory(cell ->
                new SimpleStringProperty(
                        cell.getValue().getUserStatus().toString())
        );
    }

    public void actionButtonPressed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();

        if (!(source instanceof Button)) {
            return;
        }
        User selectedItem = table.getSelectionModel().getSelectedItem();
        Button clickedButton = (Button) source;

        switch (clickedButton.getId()) {
            case "btnAddNew": {
                showDialog();
                if (contextUsersController.getUser() != null) {

                    sendInfoServer(Coms.ADD_NEW_USER
                            , "user", contextUsersController.getUser());
                   contextUsersController.setNullEntity();
                    prepareAndSetDataToTable();
                }
            }
            break;
            case "btnChange": {
                if (selectedItem != null) {
                    viewChangePatient(selectedItem);
                } else {
                    ShowException.showErrorException("Выберите запись из таблицы!");
                }
            }
            break;
            case "btnDelete": {
                if (selectedItem != null) {
                    if(selectedItem.getId().equals(Launcher.getUser().getId())){
                        ShowException.showErrorException("Данный аккаунт используется!");
                    }else {
                        sendInfoServer(Coms.DELETE_USER, "userId", selectedItem.getId());
                        prepareAndSetDataToTable();
                    }
                } else {
                    ShowException.showErrorException("Выберите запись из таблицы!");
                }
            }
            break;
        }

    }

    private void viewChangePatient(User selected) {
        if (selected != null) {
            contextUsersController.setUser(selected);
            launcherContext.showDialog();
            //
            sendInfoServer(Coms.UPDATE_USER, "user", contextUsersController.getUser());
            contextUsersController.setNullEntity();
            prepareAndSetDataToTable();
        }
    }


    private void showDialog() {
        launcherContext.showDialog();
    }


    public void actionSearch(ActionEvent actionEvent) {
        String userSurname = textFind.getText().trim();
        if (userSurname.equals("")) {
            ShowException.showErrorException("Необходимо ввести данные!");
        } else {
            ObservableList<User> newList =
                    FXCollections.observableList(
                            table.getItems()
                                    .stream()
                                    .filter(e -> e.getSurname().equalsIgnoreCase(userSurname))
                                    .collect(Collectors.toList()));

            table.setItems(newList);
        }
    }


    public void ShowAll(ActionEvent actionEvent) {
        prepareAndSetDataToTable();
    }


    public void goBack(ActionEvent actionEvent) {
        contextUsersController = null;
        Launcher.changeScene(USER_MENU_PAGE);
    }

}
