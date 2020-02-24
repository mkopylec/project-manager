package com.github.mkopylec.projectmanager.core.project;

/**
 * Secondary port
 */
public abstract class AssignedTeamRepository {

    protected abstract AssignedTeam findByName(String name);

    protected abstract void save(AssignedTeam team);
}
