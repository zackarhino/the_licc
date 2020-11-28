package com.example.fall2020androidproject.items;

public class CommentItem {
    private int image_id;
    private String username;
    private String comment;

    // Constructor
    public CommentItem(int image_id, String username, String comment) {
        this.image_id = image_id;
        this.username = username;
        this.comment = comment;
    }

    public int getImage_id() {
        return image_id;
    }
    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
}
