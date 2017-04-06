package com.github.mkopylec.projectmanager.domain.team;

import java.util.List;

public interface TeamRepository {

    boolean existsByName(String name);

    Team findByName(String name);

    List<Team> findAll();

    void save(Team team);
}
