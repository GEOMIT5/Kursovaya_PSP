package org.work.domen.com.impl.user;

import org.work.domen.com.Com;
import org.work.domen.com.Coms;
import org.work.domen.connector.Connect;
import org.work.domen.message.Message;

public class UpdateUserCommand implements Com {

    private Connect connection;

    public UpdateUserCommand(Connect connection) {
        this.connection = connection;
    }

    @Override
    public Message execute(Message request) {
        request.setCommand(Coms.UPDATE_USER);
        return connection.makeDialog(request);
    }

}
