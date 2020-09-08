package com.github.mkopylec.projectmanager.core.team;

import com.github.mkopylec.projectmanager.core.common.Committable;

import java.util.List;

/**
 * Secondary port
 */
public abstract class TeamRepository implements Committable {

    protected abstract Team findByName(String name);

    protected abstract List<Team> findAll();

    protected abstract void save(Team team);
}
