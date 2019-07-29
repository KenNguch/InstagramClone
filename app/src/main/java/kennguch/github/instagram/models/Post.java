package kennguch.github.instagram.models;

public class Post {

    private String description, imageUrl, userId, username;

    public Post() {
    }

    public Post(String description, String imageUrl, String userId) {
        this.description = description;
        this.imageUrl = imageUrl;
        this.userId = userId;
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

