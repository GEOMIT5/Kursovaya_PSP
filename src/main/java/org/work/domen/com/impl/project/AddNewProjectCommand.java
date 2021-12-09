package org.work.domen.com.impl.project;

import org.work.domen.com.Com;
import org.work.domen.com.Coms;
import org.work.domen.connector.Connect;
import org.work.domen.message.Message;


public class AddNewProjectCommand implements Com{

    private Connect connection;

    public AddNewProjectCommand(Connect connection) {
        this.connection = connection;
    }

    @Override
    public Message execute(Message request) {
        request.setCommand(Coms.ADD_NEW_PROJECT);
        return connection.makeDialog(request);
    }

}
