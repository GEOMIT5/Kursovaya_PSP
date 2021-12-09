package org.work.domen.com.impl.project;

import org.work.domen.com.Com;
import org.work.domen.com.Coms;
import org.work.domen.connector.Connect;
import org.work.domen.message.Message;


public class DeleteProjectCommand implements Com{

    private Connect connection;

    public DeleteProjectCommand(Connect connection) {
        this.connection = connection;
    }

    @Override
    public Message execute(Message request) {
        request.setCommand(Coms.DELETE_PROJECT);
        return connection.makeDialog(request);
    }


}
