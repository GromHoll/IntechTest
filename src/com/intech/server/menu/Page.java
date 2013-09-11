package com.intech.server.menu;

import com.intech.server.db.models.Content;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Page {

    public static final int ROWS_PER_PAGE = 10;

    private final List<Content> contents;
    private final String text;

    public Page(int offset, List<Content> allContents, String pageSuffix) {
        final int last = (offset+1)*ROWS_PER_PAGE;
        contents = allContents.subList(offset*ROWS_PER_PAGE, Math.min(last, allContents.size()));

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < contents.size(); i++) {
            sb.append(i);
            sb.append(") ");
            sb.append(contents.get(i).getName());
            sb.append("\n");
        }
        sb.append(pageSuffix);
        sb.append("\n");
        text = sb.toString();
    }

    public String getPageText() {
        return text;
    }

    public Content getContentAt(int index) {
        return contents.get(index);
    }
}
