package org.work.domen.connector;

import java.net.InetAddress;
import java.net.Socket;

public class Connector implements ConnectorListener {

    private static final Connector instance = new Connector();

    public static Connector getInstance() {
        return instance;
    }

    private Connector() {

    }

    private static Connect connection;

    private static int port;

    public static void setPort(int port) {
        Connector.port = port;
    }

    public static int getPort() {
        return port;
    }

    public void doCreateConnection() throws Exception {
        try {
            Socket socket = new Socket(InetAddress.getLocalHost(), port);
            connectionCreated(connection = new ConnectorListenerImpl(socket, this));
        } catch (Exception ex) {
            throw new Exception(ex);
        }
    }

    public boolean isConnectionCreated() {
        return connection != null;
    }

    public static Connect getConnection() {
        return connection;
    }

    @Override
    public void connectionCreated(Connect c) {
        System.out.println("Connection successful!");
    }

    @Override
    public void connectionClose(Connect c) {
        System.out.println("Connection ended");
        c.close();
    }

    @Override
    public void connectionException(Exception ex) {
        ex.printStackTrace();
    }

}
