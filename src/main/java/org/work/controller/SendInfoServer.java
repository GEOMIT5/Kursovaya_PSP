package org.work.controller;

import org.work.domen.com.Com;
import org.work.domen.com.Coms;
import org.work.domen.com.factory.CommandFactory;
import  org.work.domen.message.Message;




public final class SendInfoServer {

    private SendInfoServer() {
    }


    public static boolean sendInfoServer(Coms addNewPatient,
                                           String key,
                                           Object data) {

        Com command = CommandFactory.getInstance().createCommand(addNewPatient);
        Message request = new Message();
        request.add(key, data);
        Message response = command.execute(request);

        String exceptionMessage = (String) response.getByKey("ex");
        if (exceptionMessage != null) {
            ShowException.showErrorException(exceptionMessage);
            return false;
        } else {
            return true;
        }
    }

}
