package com.yrgo.bff.project.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Friends {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany
    private List<UserAccount> friendsGroup;

    public Friends() {}

    public Friends(Long friendShipId, List<UserAccount> friendsGroup) {
        this.id=id;
        this.friendsGroup=friendsGroup;
    }

    public List<UserAccount> getFriendsGroup() {
        return this.friendsGroup;
    }

    public void setFriendsGroup(List<UserAccount> friendsGroup) {
        this.friendsGroup = friendsGroup;
    }

    public Long getFriendShipId() {
        return id;
    }

    public void setFriendShipId(Long friendShipId) {
        this.id = friendShipId;
    }

    void addFriend(UserAccount user) {
        if(friendsGroup == null) {
            friendsGroup = new ArrayList<>();
        }
        this.friendsGroup.add(user);
        System.out.println("UserAccount" + getClass().getSimpleName());
        System.out.println(friendsGroup);
    }

    void removeFriend(UserAccount user) {
        this.friendsGroup.remove(user);
    }

    @Override
    public String toString() {
        return "Friends{" +
                "friendsGroup=" + friendsGroup +
                '}';
    }
}
