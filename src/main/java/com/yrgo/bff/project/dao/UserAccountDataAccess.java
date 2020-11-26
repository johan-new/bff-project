package com.yrgo.bff.project.dao;

import com.yrgo.bff.project.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserAccountDataAccess extends CrudRepository<User, String> {
    User findByUserName(String userName);
}
