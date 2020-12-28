package com.yrgo.bff.project.service;

import com.yrgo.bff.project.dao.UserAccountDataAccess;
import com.yrgo.bff.project.domain.UserAccount;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

import static java.util.Collections.emptyList;
import static java.util.Collections.sort;

@Service
public class UserAccountServiceImplementation implements UserAccountService, UserDetailsService {

    @Autowired
    UserAccountDataAccess userAccountDataAccess;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private Log log = LogFactory.getLog(getClass());

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
        passwordCheck(password);

        UserAccount user = new UserAccount(username.toLowerCase(),bCryptPasswordEncoder.encode(password));
        log.debug("createUser(" + username + ")\n" + user);
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
     * @param newUserInformation - JSON body containing new info
     * @return An instance of User that was updated
     */
    @Override @Transactional
    public UserAccount updateUser(JSONObject newUserInformation) throws Exception {
        UserAccount userAccount = readLoggedInUser();

        //mapping all data
        final String oldPassword = (String)newUserInformation.get("oldPassword");
        final String presentation = (String)newUserInformation.get("presentation");
        final String gender = ((String)newUserInformation.get("gender")).toUpperCase();
        final String city = (String)newUserInformation.get("city");
        final int age = (Integer)newUserInformation.get("age");

        String newPassword = (String)newUserInformation.get("newPassword");
        System.out.println(newPassword);
        bCryptPasswordEncoder.encode(newPassword);
        newPassword = bCryptPasswordEncoder.encode(newPassword);
        //valid data checks occur in UserAccount set-ers
        System.out.println(newPassword);
        userAccount.setPresentation(presentation);
        userAccount.setGender(UserAccount.Gender.valueOf(gender));
        userAccount.setCity(city);
        userAccount.setAge(age);

            passwordCheck(newPassword);
        userAccount.setPassword();

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
        log.debug("loadUserByUsername("+username+")");
        UserAccount user = userAccountDataAccess.findByUsername(username.toLowerCase());
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

    @Override @Transactional
    public void addFriend(String username) {
        //Tar in den nya vännen som argument
        UserAccount user = readLoggedInUser();
        //Lägger till den nya vännen på användaren
        user.addFriend(readUser(username));
    }

    @Override
    public Set<String> loadFriends(String username) {
        //ser till så att ingen kan ändra vännerna genom referensen som returneras
        Set<String> returnvalues = readUser(username).getFriends();
        log.debug("loadFriends(" + username + ")\n" + returnvalues);
        return Collections.unmodifiableSet(returnvalues);
    }

    @Override @Transactional
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

    private void passwordCheck(final String password) throws Exception {
        if (password == null || password.isBlank()) {
            throw new Exception("Password cannot be blank or null!");
        }
    }
}
