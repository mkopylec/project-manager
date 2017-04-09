package com.github.mkopylec.projectmanager.infrastructure.generators;

import com.github.mkopylec.projectmanager.domain.services.UniqueIdentifierGenerator;

import org.springframework.stereotype.Service;

import static java.util.UUID.randomUUID;

@Service
class UuidIdentifierGenerator implements UniqueIdentifierGenerator {

    @Override
    public String generateUniqueIdentifier() {
        return randomUUID().toString();
    }
}
