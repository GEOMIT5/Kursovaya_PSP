package org.work.domen.utilit.builder;

import org.work.domen.entity.User;
import org.work.domen.entity.UserStatus;


public interface UserBuilder {

    UserBuilder withLogin(String login);

    UserBuilder withPassword(String password);

    UserBuilder withName(String name);

    UserBuilder withSurname(String surname);

    UserBuilder withUserStatus(UserStatus status);

    User build();

}
