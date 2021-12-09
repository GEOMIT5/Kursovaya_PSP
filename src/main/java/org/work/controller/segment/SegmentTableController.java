package org.work.controller.segment;

import org.work.controller.ShowException;
import org.work.domen.com.Com;
import org.work.domen.com.Coms;
import org.work.domen.com.factory.CommandFactory;
import org.work.domen.config.Config;
import org.work.domen.entity.Segment;
import org.work.domen.message.Message;
import org.work.launch.Launcher;
import org.work.launch.LauncherContext;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import static org.work.controller.SendInfoServer.sendInfoServer;

public class SegmentTableController {

    @FXML
    private TableView<Segment> table;
    @FXML
    private TableColumn<Segment, String> columnName;

    private LauncherContext launcherContext = new LauncherContext();
    private SegmentContext segmentContext;

    public void initialize() {
       segmentContext = (SegmentContext) launcherContext
                .getDialogController(segmentContext, Config.CONTEXT_SEGMENT_PAGE);
        prepareAndSetDataToTable();
        initListeners();
    }


    private void prepareAndSetDataToTable() {
        initColumns();
        initList();
        table.refresh();
    }

    private void initListeners() {
        table.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                viewChange(table.getSelectionModel().getSelectedItem());
            }
        });

    }

    private void initList() {
        Com command = CommandFactory.getInstance().createCommand(Coms.GET_ALL_SEGMENT);
        Message message = new Message();
        Message response = command.execute(message);
        ObservableList<Segment> segments = (ObservableList<Segment>) response.getByKey("segments");
        table.setItems(segments);
    }


    private void initColumns() {
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    public void actionButtonPressed(ActionEvent actionEvent) {

        Object source = actionEvent.getSource();

        if (!(source instanceof Button)) {
            return;
        }
        Segment selectedItem = table.getSelectionModel().getSelectedItem();
        Button clickedButton = (Button) source;

        switch (clickedButton.getId()) {
            case "btnAddNew": {
                showDialog();
                if (segmentContext.getSegment() != null) {

                    sendInfoServer(Coms.ADD_NEW_SEGMENT
                            , "segment", segmentContext.getSegment());
                   segmentContext.setNullEntity();
                    prepareAndSetDataToTable();
                }
            }
            break;
            case "btnChange": {
                if (selectedItem != null) {
                    viewChange(selectedItem);
                } else {
                    ShowException.showErrorException("Для изменения выберите запись из таблицы!");
                }
            }
            break;
            case "btnDelete": {
                if (selectedItem != null) {
                    sendInfoServer(Coms.DELETE_SEGMENT, "segmentId", selectedItem.getId());
                    prepareAndSetDataToTable();
                } else {
                    ShowException.showErrorException("Для изменения выберите запись из таблицы!");
                }
            }
            break;
        }
    }

    private void viewChange(Segment selected) {
        if (selected != null) {
           segmentContext.setSegment(selected);
            //
            showDialog();
            //
            sendInfoServer(Coms.UPDATE_SEGMENT,
                    "segment",
                    segmentContext.getSegment());
            segmentContext.setNullEntity();
            prepareAndSetDataToTable();
        }
    }


    private void showDialog() {
        launcherContext.showDialog();
    }


    public void goBack(ActionEvent actionEvent) {
        Launcher.changeScene(Config.USER_MENU_PAGE);
    }
}