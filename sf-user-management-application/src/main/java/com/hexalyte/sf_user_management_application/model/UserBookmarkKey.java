package com.hexalyte.sf_user_management_application.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserBookmarkKey implements Serializable {

    @Column(name = "UserID")
    private Integer userId;

    @Column(name = "ServiceID")
    private Integer serviceId;

    // Constructors
    public UserBookmarkKey() {}

    public UserBookmarkKey(Integer userId, Integer serviceId) {
        this.userId = userId;
        this.serviceId = serviceId;
    }

    // Getters and Setters
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    // hashCode and equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserBookmarkKey that = (UserBookmarkKey) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(serviceId, that.serviceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, serviceId);
    }
}