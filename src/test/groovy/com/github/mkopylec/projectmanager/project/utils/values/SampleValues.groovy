package com.github.mkopylec.projectmanager.project.utils.values

import static com.github.mkopylec.projectmanager.project.inbound.http.RequestBodies.NewFeatureBody
import static com.github.mkopylec.projectmanager.project.inbound.http.RequestBodies.UpdatedFeatureBody
import static java.util.UUID.randomUUID

class SampleValues {

    static final UUID NONEXISTENT_PROJECT_ID = randomUUID()
    static final String PROJECT_NAME = 'New web framework'
    static final String OTHER_PROJECT_NAME = 'New HTTP framework'
    static final List<NewFeatureBody> EMPTY_NEW_FEATURES = []
    static final String FEATURE_NAME = 'HTTP request method handling'
    static final String OTHER_FEATURE_NAME = 'HTTP request headers handling'
    static final List<UpdatedFeatureBody> EMPTY_UPDATED_FEATURES = []
    static final String TEAM_NAME = 'Avengers'
    static final String EMPTY_TEAM_NAME = null

    private SampleValues() {
    }
}
