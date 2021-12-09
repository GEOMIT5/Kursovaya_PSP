package org.work.domen.com.impl.segment;

import org.work.domen.com.Com;
import org.work.domen.com.Coms;
import org.work.domen.connector.Connect;
import org.work.domen.message.Message;


public class DeleteSegmentCommand implements Com {

    private Connect connection;

    public DeleteSegmentCommand(Connect connection) {
        this.connection = connection;
    }

    @Override
    public Message execute(Message request) {
        request.setCommand(Coms.DELETE_SEGMENT);
        return connection.makeDialog(request);
    }

}
