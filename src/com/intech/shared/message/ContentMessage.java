package com.intech.shared.message;

import java.io.Serializable;

public class ContentMessage extends Message implements Serializable {

    private final String content;

    public ContentMessage(String content) {
        super(MessageCode.CONTENT);
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return getContent();
    }
}
