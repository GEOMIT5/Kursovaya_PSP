package org.work.domen.com.impl;

import org.work.domen.com.Com;
import org.work.domen.com.Coms;
import org.work.domen.connector.Connect;
import org.work.domen.entity.User;
import org.work.domen.message.Message;
import org.work.launch.Launcher;

import static org.work.domen.config.Config.USER_MENU_PAGE;


public class SignInCommand implements Com {

    private final Connect connection;

    public SignInCommand(Connect connection) {
        this.connection = connection;
    }

    @Override
    public Message execute(Message request) {

        request.setCommand(Coms.SIGN_IN);
        Message response = connection.makeDialog(request);
        User user = (User) response.getByKey("user");

        if (user != null) {
            Launcher.setUser(user);
            Launcher.changeScene(USER_MENU_PAGE);
        }
        return response;
    }



}
