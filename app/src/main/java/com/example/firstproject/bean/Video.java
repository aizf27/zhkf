package com.example.firstproject.bean;

public class Video {
    private String title;
    private String description;
    private String url;
    private String category; // 新增分类字段

    // 四参构造器
    public Video(String title, String description, String url, String category) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.category = category == null ? "全部" : category;
    }

    // 三参构造器（兼容旧代码）
    public Video(String title, String description, String url) {
        this(title, description, url, "全部");
    }

    // Getter & Setter
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getUrl() { return url; }
    public String getCategory() { return category; }

    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setUrl(String url) { this.url = url; }
    public void setCategory(String category) { this.category = category; }
}
