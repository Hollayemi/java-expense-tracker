package com.learning.model;

public class User {
    int id;
    String name;
    String username;
    String password;
    String profileImage;
    String bio;

    public User (String name, String username, int id, String profileImage, String bio, String password) {
        this.name = name;
        this.id = id;
        this.username = username;
        this.password = password;
        this.profileImage = profileImage;
        this.bio = bio;
    }

    public int getId () { return id; }
    public String getUsername() { return username; }
    public String getName () { return name; }
    public String getProfileImage () { return profileImage; }
    public String getBio () { return bio; }
    public String getPassword() { return password; }
}
