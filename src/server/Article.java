package server;

import java.io.Serializable;

/**
 * Created by regmoraes on 19/04/15.
 */
public class Article implements Serializable{

    private static final long serialVersionUID = 1L;

    String title;
    String content;
    String keyword;

    public Article(String keyword,String title, String content) {
        this.keyword = keyword;
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getKeyword() {
        return keyword;
    }
}
