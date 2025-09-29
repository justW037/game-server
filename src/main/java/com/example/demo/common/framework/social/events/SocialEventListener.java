package com.example.demo.common.framework.social.events;

public interface SocialEventListener {
    void onFriendRequest(String fromUserId, String toUserId);
    void onGroupMessage(String groupId, String senderId, String message);
    void onUserJoinedGroup(String groupId, String userId);
}