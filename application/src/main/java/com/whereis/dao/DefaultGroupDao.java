package com.whereis.dao;

import com.whereis.exceptions.groups.GroupWithIdentityExistsException;
import com.whereis.exceptions.groups.NoSuchGroupException;
import com.whereis.model.Group;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.math.BigInteger;
import java.security.SecureRandom;

@Transactional
@Repository("groupDao")
public class DefaultGroupDao extends AbstractDao<Group> implements GroupDao {
    private static final Logger logger = LogManager.getLogger(DefaultGroupDao.class);

    @Override
    public void save(Group group) throws GroupWithIdentityExistsException {
        if (group.getIdentity() == null) {
            SecureRandom random = new SecureRandom();
            //TODO: Make identity shorter and document its size
            String token = new BigInteger(130, 2, random).toString(32);
            group.setIdentity(token);
            getSession().persist(group);
            return;
        }
        if (getByIdentity(group.getIdentity()) == null) {
            getSession().persist(group);
        } else {
            throw new GroupWithIdentityExistsException(group.toString());
        }

    }

    @Override
    public void update(Group group) throws NoSuchGroupException {
        if (get(group.getId()) != null) {
            getSession().update(group);
        } else {
            throw new NoSuchGroupException(group.toString());
        }
    }

    @Override
    public void refresh(Group group) {
        getSession().refresh(group);
    }

    @Override
    public void merge(Group group) {
        getSession().merge(group);
    }

    @Override
    public Group getByIdentity(String identity) {
        CriteriaBuilder builder = getCriteriaBuilder();
        @SuppressWarnings("unchecked")
        CriteriaQuery<Group> criteriaQuery = createEntityCriteria();
        Root<Group> groupRoot = criteriaQuery.from(Group.class);
        criteriaQuery.select(groupRoot);
        criteriaQuery.where(builder.equal(groupRoot.get("identity"), identity));
        try {
            return getSession().createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
