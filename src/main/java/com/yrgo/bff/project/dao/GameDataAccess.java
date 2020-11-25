package com.yrgo.bff.project.dao;

import com.yrgo.bff.project.domain.Game;
import com.yrgo.bff.project.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface GameDataAccess extends CrudRepository<Game, Long> {

}
