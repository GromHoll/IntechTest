package com.intech.server.menu;

import com.intech.server.db.models.Content;

public class ContentMenuState implements MenuState {

    public static final char BACK = 'b';
    public static final String SUFFIX =
            "\n=============================\n" +
            BACK + ") back to content list.\n";

    private String text = "";
    private final UserMenu menu;

    public ContentMenuState(UserMenu menu) {
        this.menu = menu;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void processKey(char key) {
        if(key == BACK) {
            menu.setCurrentState(menu.getLookMenuState());
        }
    }
    
    public void setContent(Content content) {
        StringBuilder sb = new StringBuilder();
        sb.append(content.getName());
        sb.append("\n\n");
        sb.append(content.getText());
        sb.append(SUFFIX);

        text = sb.toString();
    }

}
