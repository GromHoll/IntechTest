package com.intech.shared.message;

import java.io.Serializable;

public class LoginMessage extends Message implements Serializable {

    private final String username;
    private final String password;

    public LoginMessage(String username, String password) {
        super(MessageCode.LOGIN);
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return getCode().name() + " : " + getUsername() + " : " + getPassword();
    }
}
