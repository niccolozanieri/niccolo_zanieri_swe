package com.example.niccolo_zanieri_swe.model;

import java.util.*;

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

    public boolean isFollowed(User u) {
         return followed.containsKey(u.username);
    }

    public void follow(User u) {
        if(this.isFollowed(u))
            throw new IllegalArgumentException("Selected user is already followed.");
    }
    
    public Map<Tags, Integer> getLikesPerTag() {
        return likesPerTag;
    }

    public void setLikesPerTag(Map<Tags, Integer> likesPerTag) {
        this.likesPerTag = likesPerTag;
    }

    public Hashtable<String, User> getFollowed() {
        return followed;
    }

    public void setFollowed(Hashtable<String, User> followed) {
        this.followed = followed;
    }

    public ArrayList<Tags> getFavouriteTags() {
        return favouriteTags;
    }

    public void setFavouriteTags(ArrayList<Tags> favouriteTags) {
        this.favouriteTags = favouriteTags;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private Map<Tags, Integer> likesPerTag = new EnumMap<>(Tags.class);
    private Hashtable<String, User> followed = new Hashtable<>();
    private ArrayList<Tags> favouriteTags = new ArrayList<>();
    private String username;
    private String email;
    private String password;
}
