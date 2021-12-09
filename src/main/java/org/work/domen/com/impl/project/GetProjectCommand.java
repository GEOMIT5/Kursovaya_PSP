package org.work.domen.com.impl.project;

import org.work.domen.com.Com;
import org.work.domen.com.Coms;
import org.work.domen.connector.Connect;
import org.work.domen.entity.Project;
import org.work.domen.message.Message;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class GetProjectCommand implements Com {

    private Connect connection;

    public GetProjectCommand(Connect connection) {
        this.connection = connection;
    }

    @Override
    public Message execute(Message request) {
        request.setCommand(Coms.GET_ALL_PROJECT);
        Message response = connection.makeDialog(request);
        List<Project> projects = (List<Project>) response.getByKey("projects");

        ObservableList<Project> companyObservableList = FXCollections.observableList(projects);

        response.add("projects", companyObservableList);

        return response;
    }


}
