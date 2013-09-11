package com.intech.server.menu;

public class MainMenuState implements MenuState {

    private final UserMenu menu;

    public static final char LOOK_LIST = '1';
    public static final char ADD_TO_LIST = '2';

    public static final String MAIN_MENU =
        LOOK_LIST   + ". Look your content list.\n" +
        ADD_TO_LIST + ". Add new content to list.\n\n" +
        "=============================\n" +
        " Use keyboard for navigation \n";

    public MainMenuState(UserMenu menu) {
        this.menu = menu;
    }

    @Override
    public String getText() {
        return MAIN_MENU;
    }

    @Override
    public void processKey(char key) {
        switch(key) {
            case LOOK_LIST:
                menu.setCurrentState(menu.getLookMenuState());
                break;
            case ADD_TO_LIST:
                menu.setCurrentState(menu.getAddMenuState());
                break;
            default:
                break;
        }
    }
}
