package org.work.controller.segment;

import org.work.controller.BaseController;
import org.work.controller.ShowException;
import org.work.domen.entity.Segment;
import org.work.domen.utilit.builder.impl.SegmentBuilderImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class SegmentContext implements BaseController{

    private Segment segment;

    @FXML
    private TextField textName;

    public void initialize() {

    }

    public void setNullEntity() {
        this.segment = null;
    }

    public Segment getSegment() {
        return segment;
    }

    public void setSegment(Segment segment) {
        if (segment == null) {
            return;
        }
        this.segment = segment;
        //
        textName.setText(segment.getName());
    }

    public void actionSave(ActionEvent actionEvent) {
        String name = textName.getText();
        if (!(name.equals(""))) {
            if (this.segment != null) {
                this.segment.setName(name);
            } else {
                this.segment = new SegmentBuilderImpl()
                        .withName(name)
                        .build();
            }

            actionClose(actionEvent);
        } else {
            ShowException.showErrorException("Заполните все поля!");
        }

    }

    public void actionClose(ActionEvent actionEvent) {
        makeClearFields();

        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }

    private void makeClearFields() {
        textName.clear();
    }

}
