package com.intech.server.menu;

import com.intech.server.db.models.Content;
import com.intech.server.db.models.Reader;

import java.util.List;

public class LookMenuState extends PagesMenuState {

    public LookMenuState(UserMenu menu, Reader reader) {
        super(menu, reader);
    }

    @Override
    public List<Content> getContents() {
        return reader.getContents();
    }

    @Override
    public void processContent(Content content) {
        menu.getContentMenuState().setContent(content);
        menu.setCurrentState(menu.getContentMenuState());
    }
}
