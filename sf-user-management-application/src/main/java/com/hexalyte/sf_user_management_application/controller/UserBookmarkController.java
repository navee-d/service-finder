package com.hexalyte.sf_user_management_application.controller;

import com.hexalyte.sf_user_management_application.model.UserBookmark;
import com.hexalyte.sf_user_management_application.service.UserBookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users/{userId}/bookmarks")
@RequiredArgsConstructor
public class UserBookmarkController {

    private final UserBookmarkService bookmarkService;

    @GetMapping
    public ResponseEntity<List<UserBookmark>> getBookmarks(@PathVariable Integer userId) {
        return ResponseEntity.ok(bookmarkService.getBookmarksByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<UserBookmark> addBookmark(@PathVariable Integer userId, @RequestBody Map<String, Integer> payload) {
        Integer serviceId = payload.get("serviceId");
        if (serviceId == null) {
            return ResponseEntity.badRequest().build();
        }
        UserBookmark newBookmark = bookmarkService.addBookmark(userId, serviceId);
        return new ResponseEntity<>(newBookmark, HttpStatus.CREATED);
    }

    @DeleteMapping("/{serviceId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "Bookmark removed successfully")
    public void removeBookmark(@PathVariable Integer userId, @PathVariable Integer serviceId) {
        bookmarkService.removeBookmark(userId, serviceId);
    }
}