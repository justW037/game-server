package com.example.demo.base.social;

import java.util.List;

import com.example.demo.common.framework.social.services.FriendService;
import com.example.demo.common.utils.ValidationUtils;

public abstract class AbstractFriendService implements FriendService {

    @Override
    public void sendFriendRequest(String fromUserId, String toUserId) {
        ValidationUtils.notBlank(fromUserId, "FromUserId must not be null or empty");
        ValidationUtils.notBlank(toUserId, "ToUserId must not be null or empty");
        ValidationUtils.notSameUser(fromUserId, toUserId, "Cannot send friend request to yourself");

        doSendFriendRequest(fromUserId, toUserId);
    }

    @Override
    public void acceptFriendRequest(String requestId) {
        ValidationUtils.notBlank(requestId, "RequestId must not be null or empty");
        doAcceptFriendRequest(requestId);
    }

    @Override
    public void blockUser(String userId, String targetUserId) {
        ValidationUtils.notBlank(userId, "UserId must not be null or empty");
        ValidationUtils.notBlank(targetUserId, "TargetUserId must not be null or empty");
        ValidationUtils.notSameUser(userId, targetUserId, "Cannot block yourself");

        doBlockUser(userId, targetUserId);
    }

    @Override
    public void unfollowUser(String userId, String targetUserId) {
        ValidationUtils.notBlank(userId, "UserId must not be null or empty");
        ValidationUtils.notBlank(targetUserId, "TargetUserId must not be null or empty");
        ValidationUtils.notSameUser(userId, targetUserId, "Cannot unfollow yourself");

        doUnfollowUser(userId, targetUserId);
    }

    @Override 
    public List<String> getFriends(String userId) {
        // Implementation logic to get friends list
        return List.of(); // Placeholder return
    }

    protected abstract void doSendFriendRequest(String fromUserId, String toUserId);

    protected abstract void doAcceptFriendRequest(String requestId);

    protected abstract void doBlockUser(String userId, String targetUserId);

    protected abstract void doUnfollowUser(String userId, String targetUserId);
}