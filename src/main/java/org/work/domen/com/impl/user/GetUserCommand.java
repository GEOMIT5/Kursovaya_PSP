package org.work.domen.com.impl.user;

import org.work.domen.com.Com;
import org.work.domen.com.Coms;
import org.work.domen.connector.Connect;
import org.work.domen.message.Message;
import org.work.domen.entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;


public class GetUserCommand  implements Com{

    private Connect connection;

    public GetUserCommand(Connect connection) {
        this.connection = connection;
    }

    @Override
    public Message execute(Message request)  {
        request.setCommand(Coms.GET_ALL_USERS);
        Message response = connection.makeDialog(request);
        List<User> users = (List<User>) response.getByKey("users");

        ObservableList<User> userObservableList = FXCollections.observableList(users);

        response.add("users", userObservableList);

        return response;
    }

}
