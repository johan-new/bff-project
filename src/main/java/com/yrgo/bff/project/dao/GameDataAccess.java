package com.yrgo.bff.project.dao;

import com.yrgo.bff.project.domain.Game;
import org.springframework.data.repository.CrudRepository;

public interface GameDataAccess extends CrudRepository<Game, Long> {

}
