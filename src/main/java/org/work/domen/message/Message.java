package org.work.domen.message;

import org.work.domen.com.Coms;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Message<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Map<String, T> request;
    private Coms command;

    public Message() {
        this.request = new HashMap<>();
    }

    public Message(Map<String, T> request, Coms command) {
        this.request = request;
        this.command = command;
    }


    public Map<String, T> getRequest() {
        return request;
    }

    public T getByKey(String key) {
        return this.request.get(key);
    }

    public Coms getCommand() {
        return command;
    }

    ///
    public void setCommand(Coms command) {
        this.command = command;
    }

    public void add(String key, T object) {
        this.request.put(key, object);
    }


}
