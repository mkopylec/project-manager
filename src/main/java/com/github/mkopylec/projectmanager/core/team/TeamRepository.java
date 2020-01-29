package com.github.mkopylec.projectmanager.core.team;

import java.util.List;

/**
 * Secondary port
 */
public abstract class TeamRepository {

    protected abstract Team findByName(String name);

    protected abstract List<Team> findAll();

    protected abstract void save(Team team);
}
