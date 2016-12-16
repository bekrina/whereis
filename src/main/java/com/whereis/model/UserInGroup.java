package com.whereis.model;

import javax.persistence.*;

@Entity
@Table(name = "usersInGroups")
public class UserInGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    //@ManyToMany
    private int userId;
    //@ManyToMany
    private int groupId;

    private boolean joined;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public boolean isJoined() {
        return joined;
    }

    public void setJoined(boolean joined) {
        this.joined = joined;
    }
}
