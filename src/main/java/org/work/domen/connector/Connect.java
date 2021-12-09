package org.work.domen.connector;

import org.work.domen.message.Message;


public interface Connect {

    int PORT =4549;

    void send(Message message);

    Message makeDialog(Message message);
    void close();

}
