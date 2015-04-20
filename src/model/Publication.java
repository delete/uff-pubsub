package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by regmoraes on 18/04/15.
 */
public class Publication{

    private static final long serialVersionUID = 1L;
    String keyword;
    List<Article> articles;


    public Publication(String keyword) {

        this.keyword = keyword;
        this.articles = new ArrayList<>();
    }

    public void addArticle(Article a) {

        articles.add(a);
    }

    public List<Article> getArticles(){

        return this.articles;
    }

    public String getKeyword() {
        return keyword;
    }
}
