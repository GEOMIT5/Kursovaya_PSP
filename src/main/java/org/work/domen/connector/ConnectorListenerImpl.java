package org.work.domen.connector;

import org.work.domen.message.Message;

import java.io.*;
import java.net.Socket;

public class ConnectorListenerImpl implements Connect,Runnable {


    private boolean needToRun = true;
    //
    private Socket socket;

    private OutputStream out;
    private InputStream in;

    public ConnectorListenerImpl(Socket socket, ConnectorListener connectorListener) throws Exception {
        super();
        this.socket = socket;
        out = socket.getOutputStream();
        in = socket.getInputStream();
        Thread t = new Thread(this);
        t.setPriority(Thread.MIN_PRIORITY);
        t.start();
    }

    @Override
    public void send(Message message) {
        try {
            ObjectOutputStream objOut = new ObjectOutputStream(out);
            objOut.writeObject(message);
            objOut.flush();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }


    @Override
    public void close() {
        try {
            this.needToRun = false;
            this.socket.close();
            this.in.close();
            this.out.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } finally {
            System.out.println("Соединение закрыто!");
        }
    }

    @Override
    public void run() {
    }


    @Override
    public Message makeDialog(Message request) {
        System.out.println("send to server: " + request.getCommand().toString());
        send(request);

        while (needToRun) {
            try {
                int amount = in.available();
                if (amount != 0) {
                    ObjectInputStream objIn = new ObjectInputStream(in);
                    Message response = (Message) objIn.readObject();
                    return response;
                } else {
                    Thread.sleep(5);
                }
            } catch (IOException | InterruptedException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }
        throw new RuntimeException();
    }


}
