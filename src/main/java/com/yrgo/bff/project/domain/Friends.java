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
    private List<UserAccount> friendsGroup = new ArrayList<>();

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
        this.friendsGroup.add(user);
    }

    void removeFriend(UserAccount user) {
        this.friendsGroup.remove(user);
    }
}
