package com.yrgo.bff.project.dao;

import com.yrgo.bff.project.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
public interface UserAccountDataAccess extends CrudRepository<User, String> {
    List<User> findByUserName(String userName);
}
