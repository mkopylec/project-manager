package com.github.mkopylec.projectmanager.utils

import com.github.mkopylec.projectmanager.core.team.JobPosition
import com.github.mkopylec.projectmanager.core.team.Member
import com.github.mkopylec.projectmanager.core.team.Team

class TeamBuilder {

    String name
    int currentlyImplementedProjects
    List<MemberBuilder> members

    protected Team build() {
        new Team(name, currentlyImplementedProjects, members.collect { it.build() })
    }
}

class MemberBuilder {

    String firstName
    String lastName
    JobPosition jobPosition

    protected Member build() {
        new Member(firstName, lastName, jobPosition)
    }
}
