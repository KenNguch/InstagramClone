package kennguch.github.instagram.models;

public class User {

    private String bio, imageUrl, userId, username;

    public User() {
    }

    public User(String bio, String imageUrl, String userId, String username) {
        this.bio = bio;
        this.imageUrl = imageUrl;
        this.userId = userId;
        this.username = username;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
