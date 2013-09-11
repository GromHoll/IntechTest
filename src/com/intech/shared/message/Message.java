package com.intech.shared.message;

import java.io.Serializable;

public class Message implements Serializable {

    private final MessageCode code;

    public Message(MessageCode code) {
        this.code = code;
    }

    public MessageCode getCode() {
        return code;
    }

    @Override
    public String toString() {
        return getCode().name();
    }
}
