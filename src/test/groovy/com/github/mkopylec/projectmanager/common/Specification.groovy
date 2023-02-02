package com.github.mkopylec.projectmanager.common

import com.github.mkopylec.projectmanager.ApplicationStarter
import com.github.mkopylec.projectmanager.common.utils.databases.MongoDb
import com.github.mkopylec.projectmanager.common.utils.time.MovableClock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@ActiveProfiles('unit-tests')
@SpringBootTest(classes = ApplicationStarter, webEnvironment = RANDOM_PORT)
abstract class Specification extends spock.lang.Specification {

    @Autowired
    private MongoDb mongoDb
    @Autowired
    protected MovableClock clock

    void cleanup() {
        mongoDb.clear()
        clock.reset()
    }
}
