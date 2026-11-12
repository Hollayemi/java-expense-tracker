package com.learning.model;

public class User {
    private int id;
    private String name;
    private String username;
    private String password; // BCrypt hash when loaded from the DB
    private String profileImage;
    private String bio;

    public User(String name, String username, int id, String profileImage, String bio, String password) {
        this.name = name;
        this.id = id;
        this.username = username;
        this.password = password;
        this.profileImage = profileImage;
        this.bio = bio;
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getName() { return name; }
    public String getProfileImage() { return profileImage; }
    public String getBio() { return bio; }
    public String getPassword() { return password; }

    /** First letter of the name, used as a fallback avatar. */
    public String getInitial() {
        return (name == null || name.isEmpty()) ? "?" : name.substring(0, 1).toUpperCase();
    }
}
