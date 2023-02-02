package com.github.mkopylec.projectmanager.common.outbound.mongodb;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import java.time.Duration;

@ConfigurationProperties("project-manager.common.outbound.mongodb")
class MongoDbProperties {

    private final Timeout timeout;

    @ConstructorBinding
    private MongoDbProperties(Timeout timeout) {
        this.timeout = timeout;
    }

    Timeout getTimeout() {
        return timeout;
    }

    static class Timeout {

        private final Duration connect;
        private final Duration read;

        @ConstructorBinding
        private Timeout(Duration connect, Duration read) {
            this.connect = connect;
            this.read = read;
        }

        Duration getConnect() {
            return connect;
        }

        Duration getRead() {
            return read;
        }
    }
}
