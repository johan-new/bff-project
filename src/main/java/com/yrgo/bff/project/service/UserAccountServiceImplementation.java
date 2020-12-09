package com.yrgo.bff.project.service;

import com.yrgo.bff.project.dao.UserAccountDataAccess;
import com.yrgo.bff.project.domain.ApplicationUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        System.out.println("Created user" + user.getUsername()+ " with password " + password);
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
    public ApplicationUser removeUser(String username) {
        ApplicationUser user = userAccountDataAccess.findByUsername(username);
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
    public ApplicationUser updateUser(String newPassword) {
        //change password
        readLoggedInUser().setPassword(newPassword);
        return readLoggedInUser();
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
        return new User(user.getUsername(), user.getPassword(), emptyList());
    }

    @Override
    public ApplicationUser readLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return readUser(authentication.getName());
    }
}
