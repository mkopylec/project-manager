package com.github.mkopylec.projectmanager.core.project.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

public class NewProjectDraft {

    private String name;

    public NewProjectDraft(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, SHORT_PREFIX_STYLE)
                .append("name", name)
                .toString();
    }

    private NewProjectDraft() {
    }
}
