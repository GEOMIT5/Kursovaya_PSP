package org.work.domen.com.impl.user;

import org.work.domen.com.Com;
import org.work.domen.com.Coms;
import org.work.domen.connector.Connect;
import org.work.domen.message.Message;
import org.work.launch.Launcher;
import static org.work.domen.config.Config.SIGN_IN_PAGE;

public class SignUpCommand  implements Com{

    private final Connect connection;

    public SignUpCommand(Connect connection) {
        this.connection = connection;
    }

    @Override
    public Message execute(Message request) {

        request.setCommand(Coms.SIGN_UP);

        Message response = connection.makeDialog(request);

        String exception = (String) response.getByKey("ex");

        if (exception == null) {
            Launcher.changeScene(SIGN_IN_PAGE);
        }


        return response;
    }


}
