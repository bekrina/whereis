package com.whereis.dao;

import com.whereis.exceptions.invites.UserAlreadyInvitedException;
import com.whereis.model.Invite;

public interface InviteDao {
    /**
     *  Methods are implemented in AbstractDao
     */
    Invite get(int id);
    boolean delete(Class <? extends Invite> type, int id);

    /**
     *  Methods specific for every implementation of this interface
     */
    void save(Invite invite) throws UserAlreadyInvitedException;
    void update(Invite invite);
    Invite getSameInvite(Invite invite);
}
