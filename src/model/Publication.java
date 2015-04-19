package model;

import java.io.Serializable;

/**
 * Created by regmoraes on 18/04/15.
 */
public class Publication implements Serializable{

    private static final long serialVersionUID = 1L;
    String keyword;
    String title;
    String content;

    public Publication(String keyword, String title, String content) {

        this.keyword = keyword;
        this.title = title;
        this.content = content;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
