package com.yrgo.bff.project.service;

import com.yrgo.bff.project.dao.UserAccountDataAccess;
import com.yrgo.bff.project.domain.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * AuthenticationServiceImplementation
 *
 * Authenticates users
 *
 * */

//TODO: Implement storing only salts/password hashes
@Service
public class AuthenticationServiceImplementation implements AuthenticationService {

    @Autowired
    UserAccountDataAccess userAccountDataAccess;

    /**
     * @param username - Username of the user that authenticates
     * @param password - Password provided by the user that tries to authenticate
     * @return a boolean value whether the authentication has succeeded
     * */
    @Override
    public boolean authenticationSuccess(String username, String password) {
        User user = userAccountDataAccess.findByUsername(username);
        password = hashThis(password);
        return user.getPassword().equals(password);
    }

    protected static String hashThis(String password){
        return new DigestUtils("SHA3-256").digestAsHex(password);
    }

}
