package org.work.domen.entity;

public class User extends AbstractEntity{

    private UserStatus userStatus;
    private String login;
    private String password;
    private String name;
    private String surname;


    public User() {
    }

    public User(String id) {
        super(id);
    }

    public User(String id, UserStatus userStatus, String login, String password,
                String name, String surname) {
        this(id);
        this.userStatus = userStatus;
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;

    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }


}
