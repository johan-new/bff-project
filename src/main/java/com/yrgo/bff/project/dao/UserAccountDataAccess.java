package com.yrgo.bff.project.dao;

import com.yrgo.bff.project.domain.ApplicationUser;
import org.springframework.data.repository.CrudRepository;

public interface UserAccountDataAccess extends CrudRepository<ApplicationUser, String> {
    /**
     * findByUserName
     *
     * Automagicly configured dao-method
     *
     * @param username - Username of the user you want to find
     * @return a user, if found, otherwise null
     * */
    ApplicationUser findByUsername(String username);
}
