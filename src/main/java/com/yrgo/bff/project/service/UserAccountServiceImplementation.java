package com.yrgo.bff.project.service;

import com.yrgo.bff.project.dao.UserAccountDataAccess;
import com.yrgo.bff.project.domain.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.Collections.emptyList;

@Service
public class UserAccountServiceImplementation implements UserAccountService, UserDetailsService {

    @Autowired
    UserAccountDataAccess userAccountDataAccess;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserAccountServiceImplementation(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    /**
     * Creates a user and persists it in the database
     *
     * @param username - String username of the user you want to create
     * @param password - String password of the user you want to create
     * @return An instance of User
     */
    @Override
    public UserAccount createUser(String username, String password) throws Exception {
        UserAccount user = new UserAccount(username.toLowerCase(),bCryptPasswordEncoder.encode(password));
        userAccountDataAccess.save(user);
        return user;
    }

    /**
     * Removes a user from the database
     *
     * @param username - String username of the user you want to remove
     * @return An instance of User that was deleted
     */
    @Override
    public UserAccount removeUser(String username) {
        UserAccount user = userAccountDataAccess.findByUsername(username.toLowerCase());
        userAccountDataAccess.delete(user);
        return user;
    }

    /**
     * Updates a user with a new password and persists it in the database
     *
     * @param newPassword - String of the new password
     * @return An instance of User that was updated
     */
    @Override
    public UserAccount updateUser(String oldPassword, String newPassword) {
        bCryptPasswordEncoder.encode(newPassword);
        readLoggedInUser().setPassword(newPassword);
        userAccountDataAccess.save(readLoggedInUser());
        return readLoggedInUser();
    }

    /**
     * Searches for a user in the database
     *
     * @param username - String username of the user you want to find
     * @return An instance of User that was found
     */
    @Override
    public UserAccount readUser(String username) {
        return userAccountDataAccess.findByUsername(username.toLowerCase());
    }

    /**
     * Fetches all users from database
     *
     * @return a Set of User
     */
    @Override
    public Set<UserAccount> findAll() {
        Set<UserAccount> users = new HashSet<UserAccount>();
        Iterator<UserAccount> ite = userAccountDataAccess.findAll().iterator();
        while (ite.hasNext()) {
            users.add(ite.next());
        }
        return users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount user = userAccountDataAccess.findByUsername(username.toLowerCase());
        System.out.println("***********\n" + user);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        User user2 = new User(user.getUsername(), user.getPassword(), emptyList());
        System.out.println(user2);
        return user2;
    }

    @Override
    public UserAccount readLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return readUser(authentication.getName());
    }

    @Override
    public void addFriend(String username) {
        //Tar in den nya vännen som argument
        UserAccount user = readLoggedInUser();
        //Lägger till den nya vännen på användaren
        user.addFriend(readUser(username));
    }

    @Override
    public List<UserAccount> loadFriends() {
       List<UserAccount> friendsList = new ArrayList<>();
        for (UserAccount u : friendsList) {
            friendsList.add(u);
        }
        return friendsList;
    }

    @Override
    public void removeFriend(String username) {
        UserAccount user = readLoggedInUser();
        user.removeFriend(readUser(username));
    }


    public static boolean validEmailAddress(String email){
        if (email.contains("@") && email.contains(".") &&
                !email.startsWith("@") && !email.endsWith("@") &&
                !email.startsWith(".") && !email.endsWith(".")) {
            return true;
        } else {
            return false;
        }
    }
}
