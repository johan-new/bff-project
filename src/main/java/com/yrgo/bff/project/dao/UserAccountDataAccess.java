package com.yrgo.bff.project.dao;

import com.yrgo.bff.project.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserAccountDataAccess extends CrudRepository<User, String> {
    /**
     * findByUserName
     *
     * Automagicly configured dao-method
     *
     * @param username - Username of the user you want to find
     * @return a user, if found, otherwise null
     * */
    User findByUsername(String username);
}
