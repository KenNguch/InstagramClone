package kennguch.github.instagram.models;

public class Post {

    private String description, imageUrl, postId, userId, username, userImage;

    public Post() {
    }

    public Post(String description, String imageUrl, String userId, String username, String postId, String userImage) {
        this.description = description;
        this.imageUrl = imageUrl;
        this.userId = userId;
        this.username = username;
        this.postId = postId;
        this.userImage = userImage;
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

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }
}

