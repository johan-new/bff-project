package com.yrgo.bff.project.dao;

import com.yrgo.bff.project.domain.UserAccount;
import org.springframework.data.repository.CrudRepository;

public interface UserAccountDataAccess extends CrudRepository<UserAccount, String> {
    /**
     * findByUserName
     *
     * Automagicly configured dao-method
     *
     * @param username - Username of the user you want to find
     * @return a user, if found, otherwise null
     * */
    UserAccount findByUsername(String username);
}
