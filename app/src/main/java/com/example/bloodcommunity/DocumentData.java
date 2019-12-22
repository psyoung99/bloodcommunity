package com.example.bloodcommunity;

import java.util.HashMap;
import java.util.Map;

public class DocumentData {
    private String title;
    private String content;
    private String id;

    public DocumentData() { }

    public DocumentData(String title, String content, String id) {
        this.title = title;
        this.content = content;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<>();

        result.put("title", title);
        result.put("content", content);

        return result;
    }
}
