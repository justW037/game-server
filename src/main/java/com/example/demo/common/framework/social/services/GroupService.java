package com.example.demo.common.framework.social.services;

import java.util.List;

public interface GroupService {
    String createGroup(String ownerId, String name);
    void inviteMember(String groupId, String userId);
    void removeMember(String groupId, String userId);
    void promoteMember(String groupId, String userId, String role);
    void sendGroupMessage(String groupId, String senderId, String message);

    List<String> getMembers(String groupId);
}