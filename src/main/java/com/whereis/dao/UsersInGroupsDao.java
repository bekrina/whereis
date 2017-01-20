package com.whereis.dao;

import com.whereis.exceptions.NoUserInGroup;
import com.whereis.exceptions.UserAlreadyInGroup;
import com.whereis.model.Group;
import com.whereis.model.User;
import com.whereis.model.UsersInGroup;

public interface UsersInGroupsDao {
    /**
     *  Methods are implemented in AbstractDao
     */
    UsersInGroup get(int id);
    void delete(UsersInGroup user);

    /**
     *  Methods specific for every implementation of this interface
     */
    void save(UsersInGroup user) throws UserAlreadyInGroup;
    void update(UsersInGroup user);
    void leave(Group group, User user) throws NoUserInGroup;

    UsersInGroup findUserInGroup(UsersInGroup usersInGroup);

    UsersInGroup findUserInGroup(Group group, User user);
}
