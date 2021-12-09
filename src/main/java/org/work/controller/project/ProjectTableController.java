package org.work.controller.project;

import org.work.controller.ShowException;
import org.work.domen.com.Com;
import org.work.domen.com.Coms;
import org.work.domen.com.factory.CommandFactory;
import org.work.domen.config.Config;
import org.work.domen.entity.Project;
import org.work.domen.entity.UserStatus;
import org.work.domen.message.Message;
import org.work.launch.Launcher;
import org.work.launch.LauncherContext;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import static org.work.controller.SendInfoServer.sendInfoServer;
import static  org.work.domen.com.Coms.*;
import static org.work.domen.config.Config.USER_MENU_PAGE;

public class ProjectTableController {

    @FXML
    private TableView<Project> table;
    @FXML
    private TableColumn<Project, String> columnSegment;
    @FXML
    private TableColumn<Project, String> columnName;
    @FXML
    private TableColumn<Project, String> columnHead;
    @FXML
    private TableColumn<Project, String> columnHumanAmount;
    @FXML
    private TableColumn<Project, String> columnAverageSalary;
    @FXML
    private TableColumn<Project, String> columnProfit;
    @FXML
    private TableColumn<Project, String> columnTerm;
    @FXML
    private TableColumn<Project, String> columnProfitability;
    @FXML
    private TableColumn<Project, String> columnStatus;
    //////////////
    @FXML
    private Button btnAddNew;
    @FXML
    private Button btnChange;
    @FXML
    private Button btnDelete;

    private static final String[] STATUS_NAMES =
            {"Долгосрочная перспективность", "Средненсрочная перспективность", "Краткосрочная перспективность", "Безперспективно"};

    private ProjectsContext projectsContext;
    private LauncherContext launcherContext = new LauncherContext();

    public void initialize() {
        projectsContext = (ProjectsContext) launcherContext
                .getDialogController(projectsContext, Config.CONTEXT_PROJECT_PAGE);
        prepareAndSetDataToTable(GET_ALL_PROJECT);
        initListeners();
        //
        if (Launcher.getUser().getUserStatus().equals(UserStatus.VIEWER)) {
            this.btnAddNew.setVisible(false);
            this.btnDelete.setVisible(false);
            btnChange.setText("Прогноз");
        }
    }

    private void prepareAndSetDataToTable(Coms action) {
        initColumns();
        initList(action);
        table.refresh();
    }

    private void initListeners() {
        table.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                viewChangePatient(table.getSelectionModel().getSelectedItem());
            }
        });

    }

    private void initList(Coms action) {
        Com command = CommandFactory.getInstance().createCommand(action);
        Message message = new Message();
        Message response = command.execute(message);
        ObservableList<Project> companies = (ObservableList<Project>) response.getByKey("projects");
        table.setItems(companies);
    }


    private void initColumns() {
        columnSegment.setCellValueFactory(cell ->
                new SimpleStringProperty(
                        cell.getValue().getSegment().getName())
        );
        columnName.setCellValueFactory(cell ->
                new SimpleStringProperty(
                        cell.getValue().getName())
        );
        columnHead.setCellValueFactory(cell ->
                new SimpleStringProperty(
                        cell.getValue().getHead())
        );

        columnHumanAmount.setCellValueFactory(cell ->
                new SimpleStringProperty(
                        String.valueOf(cell.getValue().getHumanAmount()))
        );
        columnAverageSalary.setCellValueFactory(cell ->
                new SimpleStringProperty(
                        String.valueOf(cell.getValue().getAverageSalary()))
        );
        columnProfit.setCellValueFactory(cell ->
                new SimpleStringProperty(
                        String.valueOf(cell.getValue().getProfit()))
        );
        columnTerm.setCellValueFactory(cell ->
                new SimpleStringProperty(
                        String.valueOf(cell.getValue().getTerm()))
        );
        columnProfitability.setCellValueFactory(cell ->
                new SimpleStringProperty(
                        String.valueOf(cell.getValue().getProfitability()))
        );
        columnStatus.setCellValueFactory(cell ->
                new SimpleStringProperty(
                        STATUS_NAMES[cell.getValue().getStatus().getId() - 1])
        );

    }

    public void actionButtonPressed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();

        if (!(source instanceof Button)) {
            return;
        }
        Project selectedItem = table.getSelectionModel().getSelectedItem();
        Button clickedButton = (Button) source;

        switch (clickedButton.getId()) {
            case "btnAddNew": {
                showDialog();
                if (projectsContext.getProject() != null) {

                    sendInfoServer(ADD_NEW_PROJECT
                            , "project", projectsContext.getProject());
                    projectsContext.setNullEntity();
                    prepareAndSetDataToTable(GET_ALL_PROJECT);
                }
            }
            break;
            case "btnChange": {
                if (selectedItem != null) {
                    viewChangePatient(selectedItem);
                } else {
                    ShowException.showErrorException("Для изменения выберите запись из таблицы!");
                }
            }
            break;
            case "btnDelete": {
                if (selectedItem != null) {
                    if (selectedItem.getStatus().getId() == 4) {
                        sendInfoServer(DELETE_PROJECT, "projectId", selectedItem.getId());
                        prepareAndSetDataToTable(GET_ALL_PROJECT);
                    } else {
                        ShowException.showErrorException("Нельзя удалить выбранный элемент!");
                    }
                } else {
                    ShowException.showErrorException("Для изменения выберите запись из таблицы!");
                }
            }
            break;
        }

    }

    private void viewChangePatient(Project selected) {
        if (selected != null) {
            projectsContext.setProject(selected);
            launcherContext.showDialog();
            //
            sendInfoServer(UPDATE_PROJECT, "project", projectsContext.getProject());
            projectsContext.setNullEntity();
            prepareAndSetDataToTable(GET_ALL_PROJECT);
        }
    }


    private void showDialog() {
        launcherContext.showDialog();
    }


    ///////////////////////////////////////////////////////////
    public void showAllMarked(ActionEvent actionEvent) {
        prepareAndSetDataToTable(GET_ALL_MARKED_PROJECT);
    }

    public void showAllNonMarked(ActionEvent actionEvent) {
        prepareAndSetDataToTable(GET_ALL_NOT_MARKED_PROJECT);
    }

    public void actionClose(ActionEvent actionEvent) {
        launcherContext = null;
        Launcher.changeScene(USER_MENU_PAGE);
    }


    public void showAll(ActionEvent actionEvent) {
        prepareAndSetDataToTable(GET_ALL_PROJECT);
    }


}
