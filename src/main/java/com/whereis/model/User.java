package com.whereis.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.whereis.exceptions.groups.UserAlreadyInGroupException;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.OrderBy;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.*;

@Entity
@FilterDef(name = "lastLocation")
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue
    protected int id;

    @NotNull
    @Column(nullable = false)
    protected String firstName;

    @NotNull
    @Column(nullable = false)
    protected String lastName;

    @NotNull
    @Column(nullable = false)
    protected String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @OrderBy(clause = "timestamp")
    @JsonManagedReference
    protected List<Location> locations = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    protected Set<UsersInGroup> groups = new HashSet<>();

    @OneToMany(mappedBy = "sentByUser", cascade = CascadeType.ALL)
    protected Set<Invite> sentByUser = new HashSet<>();

    @OneToMany(mappedBy = "sentToUser", cascade = CascadeType.ALL)
    protected Set<Invite> sentToUser = new HashSet<>();

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String first_name) {
        this.firstName = first_name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String last_name) {
        this.lastName = last_name;
    }

    @JsonIgnore
    public List<Location> getLocations() {
        return Collections.unmodifiableList(locations);
    }
    @JsonIgnore
    public Set<Group> getGroups() {
        Set<Group> groupsToReturn = new HashSet<>();
        for (UsersInGroup group : groups) {
            groupsToReturn.add(group.getGroup());
        }
        return Collections.unmodifiableSet(groupsToReturn);
    }

    public Set<Invite> getUserInvites() {
        return Collections.unmodifiableSet(sentToUser);
    }

    public boolean addInviteForUser(Invite invite) {
        return sentToUser.add(invite);
    }

    /**
     * Joins user to group
     * @param group target group to join to
     * @return <tt>true</tt> if user did not already join the group
     */
    public boolean joinGroup(Group group) throws UserAlreadyInGroupException {
        return groups.add(new UsersInGroup(this, group));
    }

    /**
     * Delete user from group
     * @param group to leave
     * @return <tt>true</tt> if user was in the group
     */
    public boolean leave(Group group) {
        return groups.remove(new UsersInGroup(this, group));
    }

    public boolean saveUserLocation(Location location) {
        return locations.add(location);
    }

    @Override
    public boolean equals(Object user) {
        if (user == this) return true;
        if (!(user instanceof User)) {
            return false;
        }
        User otherUser = (User) user;
        return otherUser.getId() == id
                && Objects.equals(otherUser.getEmail(), email)
                && Objects.equals(otherUser.getFirstName(), firstName)
                && Objects.equals(otherUser.getLastName(), lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, firstName, lastName);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(" id: ");
        builder.append(id);
        builder.append(" email: ");
        builder.append(email);
        builder.append(" first name: ");
        builder.append(firstName);
        builder.append(" last name: ");
        builder.append(lastName);
        return builder.toString();
    }
}
