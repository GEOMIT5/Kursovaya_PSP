package org.work.domen.com.impl.user;

import org.work.domen.com.Com;
import org.work.domen.com.Coms;
import org.work.domen.connector.Connect;
import org.work.domen.message.Message;

public class UpdatePasswordCommand implements Com {

    private Connect connection;

    public UpdatePasswordCommand(Connect connection) {
        this.connection = connection;
    }

    @Override
    public Message execute(Message request) {
        request.setCommand(Coms.UPDATE_USER_PASSWORD);
        Message response = connection.makeDialog(request);
        return response;
    }

}
