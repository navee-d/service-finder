package com.hexalyte.sf_user_management_application.service;

import com.hexalyte.sf_user_management_application.model.UserBookmark;
import java.util.List;

public interface UserBookmarkService {
    List<UserBookmark> getBookmarksByUserId(Integer userId);
    UserBookmark addBookmark(Integer userId, Integer serviceId);
    void removeBookmark(Integer userId, Integer serviceId);
}