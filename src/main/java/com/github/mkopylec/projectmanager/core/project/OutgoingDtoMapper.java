package com.github.mkopylec.projectmanager.core.project;

import com.github.mkopylec.projectmanager.core.project.dto.ExistingProject;
import com.github.mkopylec.projectmanager.core.project.dto.ExistingProjectDraft;
import com.github.mkopylec.projectmanager.core.project.dto.ExistingProjectFeature;
import com.github.mkopylec.projectmanager.core.project.dto.Requirement;
import com.github.mkopylec.projectmanager.core.project.dto.Status;

import java.util.List;

import static com.github.mkopylec.projectmanager.core.common.Utilities.convertEnum;
import static com.github.mkopylec.projectmanager.core.common.Utilities.mapElements;

class OutgoingDtoMapper {

    List<ExistingProjectDraft> mapToExistingProjectDrafts(List<Project> projects) {
        return mapElements(projects, this::mapToExistingProjectDraft);
    }

    ExistingProject mapToExistingProject(Project project) {
        return new ExistingProject(
                project.getIdentifier(),
                project.getName(),
                convertEnum(project.getStatus(), Status.class),
                project.getAssignedTeam(),
                mapElements(project.getFeatures(), this::mapToExistingProjectFeature));
    }

    private ExistingProjectDraft mapToExistingProjectDraft(Project project) {
        return new ExistingProjectDraft(
                project.getIdentifier(),
                project.getName());
    }

    private ExistingProjectFeature mapToExistingProjectFeature(Feature feature) {
        return new ExistingProjectFeature(
                feature.getName(),
                convertEnum(feature.getStatus(), Status.class),
                convertEnum(feature.getRequirement(), Requirement.class));
    }
}
