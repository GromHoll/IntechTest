package com.intech.server.menu;

import com.intech.server.db.DBHelper;
import com.intech.server.db.models.Reader;

public class UserMenu {

    private final Reader reader;
    private MenuState currentState;

    private final MainMenuState mainMenuState;
    private final ContentMenuState contentMenuState;
    private LookMenuState lookMenuState;
    private AddMenuState addMenuState;

    public UserMenu(Reader reader) {
        this.reader = reader;

        this.mainMenuState = new MainMenuState(this);
        this.lookMenuState = new LookMenuState(this, reader);
        this.contentMenuState = new ContentMenuState(this);
        this.addMenuState = new AddMenuState(this, reader);

        this.currentState = mainMenuState;
    }

    public String getCurrentMenuText() {
        return currentState.getText();
    }

    public void setCurrentState(MenuState newState) {
        currentState = newState;
    }

    public MainMenuState getMainMenuState() {
        return mainMenuState;
    }

    public LookMenuState getLookMenuState() {
        return lookMenuState;
    }

    public MenuState getAddMenuState() {
        return addMenuState;
    }

    public ContentMenuState getContentMenuState() {
        return contentMenuState;
    }

    public void refreshReader() {
        DBHelper.getInstance().refreshReader(reader);
        lookMenuState = new LookMenuState(this, reader);
        addMenuState = new AddMenuState(this, reader);
    }

    public void processKey(char key) {
        currentState.processKey(key);
    }
}
