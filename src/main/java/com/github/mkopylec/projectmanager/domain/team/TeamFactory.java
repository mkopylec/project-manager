package com.github.mkopylec.projectmanager.domain.team;

import org.springframework.stereotype.Component;

@Component
public class TeamFactory {

    public Team createTeam(String name) {
        return new Team(name, 0, members);
    }
}
