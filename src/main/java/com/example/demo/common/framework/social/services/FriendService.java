package com.example.demo.common.framework.social.services;

import java.util.List;

public interface FriendService {
    void sendFriendRequest(String fromUserId, String toUserId);
    void acceptFriendRequest(String requestId);
    void blockUser(String userId, String targetUserId);
    void unfollowUser(String userId, String targetUserId);

    List<String> getFriends(String userId);
    List<String> getFriendRequests(String userId);
    List<String> getBlockedUsers(String userId);
}
