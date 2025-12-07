package com.hexalyte.sf_user_management_application.service.impl;

import com.hexalyte.sf_user_management_application.model.User;
import com.hexalyte.sf_user_management_application.model.UserBookmark;
import com.hexalyte.sf_user_management_application.model.UserBookmarkKey;
import com.hexalyte.sf_user_management_application.repository.UserBookmarkRepository;
import com.hexalyte.sf_user_management_application.repository.UserRepository;
import com.hexalyte.sf_user_management_application.service.UserBookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserBookmarkServiceImpl implements UserBookmarkService {

    private final UserBookmarkRepository bookmarkRepository;
    private final UserRepository userRepository; // To link the bookmark to a user

    @Override
    public List<UserBookmark> getBookmarksByUserId(Integer userId) {
        // We can't query by just userId, so we filter the list
        return bookmarkRepository.findAll().stream()
                .filter(bookmark -> bookmark.getId().getUserId().equals(userId))
                .collect(Collectors.toList());
        // A better way would be adding:
        // List<UserBookmark> findById_UserId(Integer userId);
        // to UserBookmarkRepository.java
    }

    @Override
    public UserBookmark addBookmark(Integer userId, Integer serviceId) {
        // Find the local user first
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with ID: " + userId)
        );

        UserBookmarkKey key = new UserBookmarkKey(userId, serviceId);
        if (bookmarkRepository.existsById(key)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Bookmark already exists.");
        }

        UserBookmark newBookmark = new UserBookmark();
        newBookmark.setId(key);
        newBookmark.setUser(user);

        return bookmarkRepository.save(newBookmark);
    }

    @Override
    public void removeBookmark(Integer userId, Integer serviceId) {
        UserBookmarkKey key = new UserBookmarkKey(userId, serviceId);
        if (!bookmarkRepository.existsById(key)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bookmark not found.");
        }
        bookmarkRepository.deleteById(key);
    }
}