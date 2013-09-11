package com.intech.shared.message;

import java.io.Serializable;

public class KeyMessage extends Message implements Serializable {

    private final char key;

    public KeyMessage(char key) {
        super(MessageCode.KEY);
        this.key = key;
    }

    public char getKey() {
        return key;
    }

    @Override
    public String toString() {
        return Character.toString(getKey());
    }
}
