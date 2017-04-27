package com.baiheplayer.desk.chest;

/**
 * Created by Administrator on 2017/3/21.
 */

public class Book {

    private String id;
    private String uriCover;
    private String name;
    private String author;
    private String ISBN;
    private String summary;
    private String catalog;
    private String mark;
    private boolean isCK;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUriCover() {
        return uriCover;
    }

    public void setUriCover(String uriCover) {
        this.uriCover = uriCover;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public boolean isCK() {
        return isCK;
    }

    public void setCK(boolean CK) {
        isCK = CK;
    }
}
