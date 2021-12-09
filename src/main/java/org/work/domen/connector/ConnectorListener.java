package org.work.domen.connector;

public interface ConnectorListener {

    void connectionCreated(Connect c);

    void connectionClose(Connect c);

    void connectionException(Exception ex);


}
