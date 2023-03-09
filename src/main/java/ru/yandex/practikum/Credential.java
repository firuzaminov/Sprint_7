package ru.yandex.practikum;

public class Credential {
    private String login;
    private String password;

    public Credential(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Credential() {
    }

    public static Credential from (Courier courier) {
        return new Credential(courier.getLogin(), courier.getPassword());
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
}