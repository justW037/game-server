package com.example.demo.app.social.services;

import java.util.List;

import com.example.demo.base.social.AbstractFriendService;

public class FriendServiceImpl extends AbstractFriendService {
    @Override
    public void doSendFriendRequest(String fromUserId, String toUserId) {
        // Implementation logic to send a friend request
    }

    @Override
    public void doAcceptFriendRequest(String requestId) {
        // Implementation logic to accept a friend request
    }

    @Override
    public void doBlockUser(String userId, String targetUserId) {
        // Implementation logic to block a user
    }

    @Override
    public void doUnfollowUser(String userId, String targetUserId) {
        // Implementation logic to unfollow a user
    }

    @Override
    public List<String> getFriends(String userId) {
        // Implementation logic to get friends list
        return List.of(); // Placeholder return
    }

    @Override
    public List<String> getFriendRequests(String userId) {
        // Implementation logic to get friend requests
        return List.of(); // Placeholder return
    }

    @Override
    public List<String> getBlockedUsers(String userId) {
        // Implementation logic to get blocked users
        return List.of(); // Placeholder return
    }
    
}
