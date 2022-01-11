package com.niccolo_zanieri_swe.model;

import java.util.*;

enum Tags {
    LANDSCAPE, PORTRAIT, STREET, ACTION, ADVENTURE, COMEDY, THRILLER, VISUAL_IDENTITY, ADVERTISING, UI, MOTION_GRAPHIC
}

public abstract class User {
    public User(String usr, String email, String psw) {
        this.username = usr;
        this.email = email;
        this.password = psw;
    }

    public User(String usr, String email, String psw, Tags[] tags) {
        this(usr, email, psw);

        Collections.addAll(favouriteTags, tags);
        for(Tags tag : Tags.values()) {
            likesPerTag.put(tag, 0);
        }
    }

    public boolean isFollowed(User u) {
        return followed.containsKey(u.username);
    }

    public void follow(User u) {
    if(this.isFollowed(u) || u == this)
            throw new IllegalArgumentException("Can't follow yourself or someone already followed.");
        else {
            followed.put(u.username, u);
        }
    }

    public Map<Tags, Integer> getLikesPerTag() {
        return likesPerTag;
    }

     Hashtable<String, User> getFollowed() {
        return followed;
    }

    public ArrayList<Tags> getFavouriteTags() {
        return favouriteTags;
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

    public int[] getLikesIntArray() {
        int[] likes = new int[likesPerTag.size()];
        int i = 0;
        for(Integer j : likesPerTag.values()) {
            likes[i] = j;
            i++;
        }

        return likes;
    }

    private Map<Tags, Integer> likesPerTag = new EnumMap<>(Tags.class);
    private Hashtable<String, User> followed = new Hashtable<>();
    private ArrayList<Tags> favouriteTags = new ArrayList<>();
    private String username;
    private String email;
    private String password;
}