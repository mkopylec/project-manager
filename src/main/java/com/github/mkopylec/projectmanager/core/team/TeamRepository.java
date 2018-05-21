package com.github.mkopylec.projectmanager.core.team;

import java.util.List;

/**
 * Secondary port
 */
public interface TeamRepository {

    boolean existsByName(String name);

    Team findByName(String name);

    List<Team> findAll();

    void save(Team team);
}
