package com.example.niccolo_zanieri_swe.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

enum Tags {
    LANDSCAPE, PORTRAIT, STREET, ACTION, ADVENTURE, COMEDY, THRILLER, VISUAL_IDENTITY, ADVERTISING, UI, MOTION_GRAPHIC
}

public abstract class User {
    public User(String usr, String email, String psw, Tags[] tags) {
        username = usr;
        this.email = email;
        password = psw;

        Collections.addAll(favouriteTags, tags);
        for(Tags tag : Tags.values()) {
            likesPerTag.put(tag, 0);
        }
    }

    private Map<Tags, Integer> likesPerTag = new EnumMap<>(Tags.class);
    private ArrayList<User> followers = new ArrayList<>();
    private ArrayList<Tags> favouriteTags = new ArrayList<>();
    private String username;
    private String email;
    private String password;
}
