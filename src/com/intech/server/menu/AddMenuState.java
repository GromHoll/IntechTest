package com.intech.server.menu;

import com.intech.server.db.DBHelper;
import com.intech.server.db.models.Content;
import com.intech.server.db.models.Reader;

import java.util.List;

public class AddMenuState extends PagesMenuState {

    public AddMenuState(UserMenu menu, Reader reader) {
        super(menu, reader);
    }

    @Override
    public List<Content> getContents() {
        return DBHelper.getInstance().getNotReaderContents(reader);
    }

    @Override
    public void processContent(Content content) {
        reader.getContents().add(content);
        DBHelper.getInstance().saveReader(reader);
        menu.refreshReader();
        menu.setCurrentState(menu.getMainMenuState());
    }
}
