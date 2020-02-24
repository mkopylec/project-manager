package com.github.mkopylec.projectmanager.core.project;

import static com.github.mkopylec.projectmanager.core.common.Utilities.allEmpty;
import static com.github.mkopylec.projectmanager.core.common.Utilities.isEmpty;
import static com.github.mkopylec.projectmanager.core.project.AssignedTeamRequirementsValidator.assignedTeamRequirements;

// TODO Don't treat as aggregate, remove repository etc., load or save using mongodb team repository in infra when finding or saving project?
public class AssignedTeam {

    private String name;
    private int currentlyImplementedProjects;

    private AssignedTeam(String name, int currentlyImplementedProjects) {
        assignedTeamRequirements()
                .requireName(name)
                .requireValidCurrentlyImplementedProjects(currentlyImplementedProjects)
                .validate();
        this.name = name;
        this.currentlyImplementedProjects = currentlyImplementedProjects;
    }

    boolean hasInvalidCurrentlyImplementedProjects() {
        return currentlyImplementedProjects < 0;
    }

    boolean isUnnamed() {
        return isEmpty(name);
    }

    void addCurrentlyImplementedProject() {
        currentlyImplementedProjects++;
    }

    void removeCurrentlyImplementedProject() {
        currentlyImplementedProjects--;
    }

    public static class AssignedTeamPersistenceHelper {

        public AssignedTeam createTeam(String name, int currentlyImplementedProjects) {
            try {
                return allEmpty(name, currentlyImplementedProjects) ? null : new AssignedTeam(name, currentlyImplementedProjects);
            } catch (Exception e) {
                throw new IllegalStateException("Error creating assigned team from persistent state", e);
            }
        }

        public String getName(AssignedTeam team) {
            return team.name;
        }

        public int getCurrentlyImplementedProjects(AssignedTeam team) {
            return team.currentlyImplementedProjects;
        }
    }
}
