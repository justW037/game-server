package com.example.demo.base.social;

import com.example.demo.common.framework.social.services.GroupService;

public abstract class AbstractGroupService implements GroupService {

    @Override
    public String createGroup(String ownerId, String name) {
        if (ownerId == null || ownerId.isBlank()) {
            throw new IllegalArgumentException("OwnerId must not be null or empty");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Group name must not be null or empty");
        }
        return doCreateGroup(ownerId, name);
    }

    @Override
    public void inviteMember(String groupId, String userId) {
        validateId(groupId, "GroupId");
        validateId(userId, "UserId");
        doInviteMember(groupId, userId);
    }

    protected abstract String doCreateGroup(String ownerId, String name);
    protected abstract void doInviteMember(String groupId, String userId);

    private void validateId(String id, String fieldName) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be null or empty");
        }
    }
}