package com.example.niccolo_zanieri_swe.model;

enum MediaType {
    PHOTO, VIDEO, GRAPHIC
}

public class Post {
    public Post(MediaType type, String file, String description) {
        this.type = type;
        this.file = file;
        this.description = description;
    }

    private MediaType type;
    private String file;
    private String description;
}
