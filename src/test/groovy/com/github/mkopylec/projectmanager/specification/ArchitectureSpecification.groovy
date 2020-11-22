package com.github.mkopylec.projectmanager.specification

import com.tngtech.archunit.core.importer.ClassFileImporter
import spock.lang.Specification

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes

class ArchitectureSpecification extends Specification {

    private static final String ALL = 'com.github.mkopylec.projectmanager'
    private static final String API = "${ALL}.api.."
    private static final String CORE = "${ALL}.core.."
    private static final String IO = "${ALL}.io.."

    def "Should maintain a correct architecture"() {
        given:
        def allClasses = new ClassFileImporter().importPackages(ALL)

        when:
        def rule = classes()
                .that().resideInAPackage(API)
                .should().onlyBeAccessed().byAnyPackage(API, CORE)

        then:
        rule.check(allClasses)

        when:
        rule = classes()
                .that().resideInAPackage(CORE)
                .should().onlyBeAccessed().byAnyPackage(CORE)

        then:
        rule.check(allClasses)

        when:
        rule = classes()
                .that().resideInAPackage(IO)
                .should().onlyBeAccessed().byAnyPackage(IO)

        then:
        rule.check(allClasses)
    }
}
