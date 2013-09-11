package com.intech.server.menu;

import com.intech.server.db.models.Content;
import com.intech.server.db.models.Reader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class PagesMenuState implements MenuState {

    public static final char NEXT = 'n';
    public static final char PREVIOUS = 'p';
    public static final char BACK = 'b';

    public static final String EMPTY =
            "Can't find content for this category.\n" +
            BACK + ") back to main menu.";

    protected final UserMenu menu;
    protected final Reader reader;

    protected final ArrayList<Page> pages = new ArrayList<Page>();
    protected int currentPage = 0;
    protected int totalPage;

    public PagesMenuState(UserMenu menu, Reader reader) {
        this.menu = menu;
        this.reader = reader;
        generatePages();
    }

    private void generatePages() {
        List<Content> contents = getContents();

        final int size = contents.size();
        totalPage = (size + Page.ROWS_PER_PAGE - 1)/Page.ROWS_PER_PAGE;

        final String prefix =
            "\n=============================\n" +
            BACK + ") back to main menu.\n" +
            NEXT + ") next page.\n" +
            PREVIOUS + ") previous page.\n" +
            "=============================\n";
        final String suffix = " page.\n";

        for(int i = 0; i < totalPage; i++) {
            pages.add(new Page(i, contents,
                               prefix + (i + 1) + '/' + totalPage + suffix));
        }
    }

    @Override
    public String getText() {
        if(!pages.isEmpty()) {
            return pages.get(currentPage).getPageText();
        } else {
            return EMPTY;
        }
    }

    public void nextPage() {
        currentPage = (currentPage + 1)%totalPage;
    }

    public void previousPage() {
        currentPage = (currentPage + totalPage - 1)%totalPage;
    }

    public abstract List<Content> getContents();

    @Override
    public void processKey(char key) {

        if(pages.isEmpty() && key == BACK) {
            menu.setCurrentState(menu.getMainMenuState());
        } else {
            if(Character.isDigit(key)) {
                Content content = pages.get(currentPage).getContentAt(Character.digit(key, 10));
                processContent(content);
            } else {
                switch(key) {
                    case NEXT:
                        nextPage();
                        break;
                    case PREVIOUS:
                        previousPage();
                        break;
                    case BACK:
                        menu.setCurrentState(menu.getMainMenuState());
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public abstract void processContent(Content content);
}
