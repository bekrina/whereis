package com.whereis.dao;

import com.whereis.exceptions.GroupWithIdentityExists;
import com.whereis.exceptions.NoSuchGroup;
import com.whereis.model.Group;
import com.whereis.model.Invite;
import com.whereis.model.User;

public interface GroupDao {
    /**
     *  Methods are implemented in AbstractDao
     *  @see AbstractDao
     */
    Group get(int id);
    void delete(Group group);

    /**
     *  Methods specific for every implementation of this interface
     */
    void save(Group group) throws GroupWithIdentityExists;
    void update(Group group) throws NoSuchGroup;
    Group getByIdentity(String identity);
}
