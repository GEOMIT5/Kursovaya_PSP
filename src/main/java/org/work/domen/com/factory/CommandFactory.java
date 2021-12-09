package org.work.domen.com.factory;

import org.work.domen.com.Com;
import org.work.domen.com.Coms;
import org.work.domen.com.impl.SignInCommand;
import org.work.domen.com.impl.project.*;
import org.work.domen.com.impl.segment.AddNewSegmentCommand;
import org.work.domen.com.impl.segment.DeleteSegmentCommand;
import org.work.domen.com.impl.segment.GetSegmentCommand;
import org.work.domen.com.impl.segment.UpdateSegmentCommand;
import org.work.domen.com.impl.user.*;
import org.work.domen.connector.Connect;
import org.work.domen.connector.Connector;


public class CommandFactory {

    private static final CommandFactory instance = new CommandFactory();

    public static CommandFactory getInstance() {
        return instance;
    }

    private CommandFactory() {

    }

    private static final Connect connection = Connector.getConnection();

    public Com createCommand(Coms command) {

        switch (command) {
            case SIGN_IN:
                return new SignInCommand(connection);

            /////
            case SIGN_UP:
                return new SignUpCommand(connection);
            case GET_ALL_USERS:
                return new GetUserCommand(connection);
            case UPDATE_USER:
                return new UpdateUserCommand(connection);
            case ADD_NEW_USER:
                return new AddNewUserCommand(connection);
            case DELETE_USER:
                return new DeleteUserCommand(connection);
            case UPDATE_USER_PASSWORD:
                return new UpdatePasswordCommand(connection);
            /////
            case ADD_NEW_SEGMENT:
                return new AddNewSegmentCommand(connection);
            case UPDATE_SEGMENT:
                return new UpdateSegmentCommand(connection);
            case DELETE_SEGMENT:
                return new DeleteSegmentCommand(connection);
            case GET_ALL_SEGMENT:
                return new GetSegmentCommand(connection);
            /////
            case ADD_NEW_PROJECT:
                return new AddNewProjectCommand(connection);
            case UPDATE_PROJECT:
                return new UpdateProjectCommand(connection);
            case DELETE_PROJECT:
                return new DeleteProjectCommand(connection);
            case GET_ALL_PROJECT:
                return new GetProjectCommand(connection);
            /////

            case GET_ALL_MARKED_PROJECT:
                return new GetMarkedCommand(connection);
            case GET_ALL_NOT_MARKED_PROJECT:
                return new GetNotMarkedCommand(connection);


        }

        throw new RuntimeException("NO COMMAND");
    }


}
