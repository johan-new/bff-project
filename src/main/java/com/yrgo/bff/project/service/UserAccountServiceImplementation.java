package com.yrgo.bff.project.service;

import com.yrgo.bff.project.dao.UserAccountDataAccess;
import com.yrgo.bff.project.domain.ApplicationUser;
import com.yrgo.bff.project.security.UserDetailed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static java.util.Collections.emptyList;

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
    public ApplicationUser createUser(final String username, String password) {
//        password = AuthenticationServiceImplementation.hashThis(password);
        ApplicationUser user = new ApplicationUser(username, password);
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
    public ApplicationUser removeUser(String username, String password) {
        ApplicationUser user = userAccountDataAccess.findByUsername(username);
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
    public ApplicationUser updateUser(String username, String password, String newPassword) {
        ApplicationUser user = userAccountDataAccess.findByUsername(username);
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
    public ApplicationUser readUser(String username, String password) {
        return userAccountDataAccess.findByUsername(username);
    }

    /**
     * Searches for a user in the database
     *
     * @param username - String username of the user you want to find
     * @return An instance of User that was found
     */
    @Override
    public ApplicationUser readUser(String username) {
        return userAccountDataAccess.findByUsername(username);
    }

    /**
     * Fetches all users from database
     *
     * @return a Set of User
     */
    @Override
    public Set<ApplicationUser> findAll() {
        Set<ApplicationUser> users = new HashSet<ApplicationUser>();
        Iterator<ApplicationUser> ite = userAccountDataAccess.findAll().iterator();
        while (ite.hasNext()) {
            users.add(ite.next());
        }
        return users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser user = userAccountDataAccess.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        UserDetailed ud = new UserDetailed(user);
        System.out.println(ud.getUsername() + ud.getPassword());
        return new User(user.getUsername(), user.getPassword(), emptyList());
    }
}
