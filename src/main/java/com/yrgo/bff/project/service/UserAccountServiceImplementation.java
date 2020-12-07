package com.yrgo.bff.project.service;

import com.yrgo.bff.project.dao.UserAccountDataAccess;
import com.yrgo.bff.project.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import static java.util.Collections.emptyList;
import org.springframework.security.core.userdetails.User;

@Service
public class UserAccountServiceImplementation implements UserAccountService, UserDetailsService {

    @Autowired
    UserAccountDataAccess userAccountDataAccess;

    /**
     * Creates a user and persists it in the database
     *
     * @param username - String username of the user you want to create
     * @param password - String password of the user you want to create
     * @return An instance of User
     */
    @Override
    public User createUser(final String username, String password) {
        password = AuthenticationServiceImplementation.hashThis(password);
        User user = new User(username, password);
        System.out.println("Created user with password " + password);
        userAccountDataAccess.save(user);
        return user;
    }

    /**
     * Removes a user from the database
     *
     * @param username - String username of the user you want to remove
     * @param password - String password of the user you want to remove
     * @return An instance of User that was deleted
     */
    @Override
    public User removeUser(String username, String password) {
        User user = userAccountDataAccess.findByUserName(username);
        userAccountDataAccess.delete(user);
        return user;
    }

    /**
     * Updates a user with a new password and persists it in the database
     *
     * @param username    - String of the user you want to update
     * @param password    - String of the user you want to update
     * @param newPassword - String of the new password
     * @return An instance of User that was updated
     */
    @Override
    public User updateUser(String username, String password, String newPassword) {
        User user = userAccountDataAccess.findByUserName(username);
        user.setPassword(newPassword);
        userAccountDataAccess.save(user);
        return user;
    }

    /**
     * Searches for a user in the database
     *
     * @param username - String username of the user you want to find
     * @param password - String password of the user you want to find
     * @return An instance of User that was found
     */
    @Override
    public User readUser(String username, String password) {
        return userAccountDataAccess.findByUserName(username);
    }

    /**
     * Searches for a user in the database
     *
     * @param username - String username of the user you want to find
     * @return An instance of User that was found
     */
    @Override
    public User readUser(String username) {
        return userAccountDataAccess.findByUserName(username);
    }

    /**
     * Fetches all users from database
     *
     * @return a Set of User
     */
    @Override
    public Set<User> findAll() {
        Set<User> users = new HashSet<User>();
        Iterator<User> ite = userAccountDataAccess.findAll().iterator();
        while (ite.hasNext()) {
            users.add(ite.next());
        }
        return users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userAccountDataAccess.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(user.getUserName(), user.getPassword());
    }
}
