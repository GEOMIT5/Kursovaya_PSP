package org.work.domen.com.impl.segment;

import org.work.domen.com.Com;
import org.work.domen.com.Coms;
import org.work.domen.connector.Connect;
import org.work.domen.message.Message;

public class AddNewSegmentCommand implements Com {

    private Connect connection;

    public AddNewSegmentCommand(Connect connection) {
        this.connection = connection;
    }

    @Override
    public Message execute(Message request) {
        request.setCommand(Coms.ADD_NEW_SEGMENT);

        Message response = connection.makeDialog(request);

        return response;
    }



}
