package my.blog.domain;

import lombok.Data;

@Data
public class Post {

    private Long id;
    private String title;
    private String name;
    private String date;
    private  String content;

    public Post() {}

    public Post(Long id, String title, String name, String date, String content) {
        this.id = id;
        this.title = title;
        this.name = name;
        this.date = date;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
