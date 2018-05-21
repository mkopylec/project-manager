package com.github.mkopylec.projectmanager.infrastructure.services;

import com.github.mkopylec.projectmanager.core.project.UniqueIdentifierGenerator;
import org.springframework.stereotype.Service;

import static java.util.UUID.randomUUID;

/**
 * Secondary adapter
 */
@Service
class UuidIdentifierGenerator implements UniqueIdentifierGenerator {

    @Override
    public String generateUniqueIdentifier() {
        return randomUUID().toString();
    }
}
