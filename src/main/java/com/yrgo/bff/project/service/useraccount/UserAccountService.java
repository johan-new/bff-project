package com.yrgo.bff.project.service.useraccount;

import com.yrgo.bff.project.domain.UserAccount;
import org.json.simple.JSONObject;

import java.util.Set;

public interface UserAccountService {
    UserAccount createUser(String username, String password) throws Exception;
    UserAccount removeUser(String username);
    UserAccount updateUser(JSONObject newUserInformation) throws Exception;
    UserAccount readUser(String username);
    UserAccount readLoggedInUser();

    Set<UserAccount> findAll();

    void addFriend(String username);
    void removeFriend(String username);

    Set<String> loadFriends(String username);

}
