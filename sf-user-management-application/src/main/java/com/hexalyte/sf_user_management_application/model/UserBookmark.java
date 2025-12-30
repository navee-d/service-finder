package com.hexalyte.sf_user_management_application.model;

// import jakarta.mail.Service; // ❌ REMOVED WRONG IMPORT
import jakarta.persistence.*;

@Entity
@Table(name = "userbookmarks") // Matches SQL dump table name
public class UserBookmark {

    @EmbeddedId
    private UserBookmarkKey id;

    @ManyToOne
    @MapsId("userId") // Maps the 'userId' field in UserBookmarkKey
    @JoinColumn(name = "UserID", nullable = false)
    private User user;

    // ❌ REMOVED THE INCORRECT SERVICE FIELD.
    // The 'serviceId' is already stored in the UserBookmarkKey.
    // We should not create a @ManyToOne link across microservices.

    public UserBookmark() {}

    // Getters and Setters
    public UserBookmarkKey getId() {
        return id;
    }

    public void setId(UserBookmarkKey id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // ❌ REMOVED getService() and setService()
}