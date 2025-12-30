package com.hexalyte.sf_user_management_application.repository;

import com.hexalyte.sf_user_management_application.model.UserBookmark;
import com.hexalyte.sf_user_management_application.model.UserBookmarkKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBookmarkRepository extends JpaRepository<UserBookmark, UserBookmarkKey> {
    // You can add custom queries based on the key, e.g.:
    // List<UserBookmark> findById_UserId(Integer userId);
}