package com.whereis.dao;

import com.whereis.AbstractIntegrationTest;
import com.whereis.exceptions.groups.GroupWithIdentityExistsException;
import com.whereis.exceptions.invites.UserAlreadyInvitedException;
import com.whereis.exceptions.users.UserWithEmailExistsException;
import com.whereis.model.Group;
import com.whereis.model.Invite;
import com.whereis.model.User;
import com.whereis.testconfig.TestHibernateConfiguration;
import com.whereis.testconfig.TestWebMvcConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.Timestamp;

@ContextConfiguration(classes = {TestHibernateConfiguration.class, TestWebMvcConfiguration.class})
@WebAppConfiguration
public class DefaultInviteDaoIT extends AbstractIntegrationTest {
    @Autowired
    InviteDao inviteDao;

    @Autowired
    GroupDao groupDao;

    @Autowired
    UserDao userDao;

    private Invite defaultInvite;
    private User defaultUser;
    private User defaultUser2;
    private Group defaultGroup;

    @BeforeMethod
    public void setupTestData() {
        setupDefaultUsers();
        setupDefaultGroup();
        setupDefaultInvite();
    }

    private void setupDefaultUsers() {
        defaultUser = new User();
        defaultUser.setEmail("sweetpotatodevelopment@gmail.com");
        defaultUser.setFirstName("Potato");
        defaultUser.setLastName("Development");

        defaultUser2 = new User();
        defaultUser2.setEmail("abekrina@gmail.com");
        defaultUser2.setFirstName("Alena");
        defaultUser2.setLastName("Bekrina");
    }

    private void setupDefaultGroup() {
        defaultGroup = new Group();
        defaultGroup.setIdentity("12345");
        defaultGroup.setName("Default group");
    }

    private void setupDefaultInvite() {
        defaultInvite = new Invite();
        defaultInvite.setGroup(defaultGroup);
        defaultInvite.setSentByUser(defaultUser2);
        defaultInvite.setSentToUser(defaultUser);
    }

    @Test
    public void testSaveInvite() throws UserAlreadyInvitedException, GroupWithIdentityExistsException, UserWithEmailExistsException {
        userDao.save(defaultUser);
        groupDao.save(defaultGroup);
        inviteDao.save(defaultInvite);

        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();

        Assert.assertEquals(inviteDao.get(defaultInvite.getId()), defaultInvite);
        Assert.assertNotNull(inviteDao.get(defaultInvite.getId()).getTimestamp());
    }

    @Test(expectedExceptions = UserAlreadyInvitedException.class)
    public void testSaveDuplicateInvite() throws UserAlreadyInvitedException, GroupWithIdentityExistsException, UserWithEmailExistsException {
        userDao.save(defaultUser);
        groupDao.save(defaultGroup);
        inviteDao.save(defaultInvite);

        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();

        Assert.assertEquals(inviteDao.get(defaultInvite.getId()), defaultInvite);
        inviteDao.save(defaultInvite);
    }

    @Test
    public void testGetSameInvite() throws UserAlreadyInvitedException, GroupWithIdentityExistsException, UserWithEmailExistsException {
        userDao.save(defaultUser);
        groupDao.save(defaultGroup);
        inviteDao.save(defaultInvite);

        Invite sameInvite = new Invite();
        sameInvite.setGroup(defaultInvite.getGroup());
        sameInvite.setSentToUser(defaultInvite.getSentToUser());
        sameInvite.setSentByUser(defaultInvite.getSentByUser());

        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();

        Assert.assertEquals(inviteDao.getSameInvite(sameInvite), defaultInvite);
    }

    @Test
    public void testGetSameInviteIfNotExists() throws GroupWithIdentityExistsException, UserWithEmailExistsException {
        userDao.save(defaultUser);
        groupDao.save(defaultGroup);

        Assert.assertNull(inviteDao.getSameInvite(defaultInvite));
    }
}