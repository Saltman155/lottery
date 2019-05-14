package com.superywd.lottery.main.model;

/**
 * @author 迷宫的中心
 * @date 2019/5/14 23:17
 */
public class TbTactic {

    private Integer id;

    private String title;

    private String description;

    private TbAdmin author;

    public TbTactic() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TbAdmin getAuthor() {
        return author;
    }

    public void setAuthor(TbAdmin author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "TbTactic{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", author=" + author +
                '}';
    }
}
