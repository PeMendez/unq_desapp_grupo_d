package com.desapp.crypto_exchange

import com.tngtech.archunit.base.DescribedPredicate
import com.tngtech.archunit.core.domain.JavaClass
import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.core.importer.ImportOption
import com.tngtech.archunit.lang.ArchRule
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition
import com.tngtech.archunit.library.Architectures
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest
class ArchitectureTest {
    lateinit var baseClasses: JavaClasses
    @BeforeEach
    fun setup() {
        baseClasses = ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.desapp.crypto_exchange")
    }

    @Test
    fun layeredArchitectureTest(){
        val rule : ArchRule = Architectures.layeredArchitecture()
            .consideringAllDependencies()
            .layer("Controller").definedBy("..controller..", "..dataInit..")
            .layer("Service").definedBy("..service..", "..thirdApiService..")
            .layer("Persistence").definedBy("..repository..")
            .layer("Security").definedBy("..security..")

            .whereLayer("Controller").mayNotBeAccessedByAnyLayer()
            .whereLayer("Service").mayOnlyBeAccessedByLayers("Controller", "Security")
            .whereLayer("Persistence").mayOnlyBeAccessedByLayers("Service")
            .whereLayer("Security").mayNotBeAccessedByAnyLayer()
        rule.check(baseClasses)
    }

    @Test
    fun controllerClassesShouldEndWithController(){
        ArchRuleDefinition.classes().that().resideInAPackage("..controller")
            .and(object : DescribedPredicate<JavaClass>("are not Kotlin generated classes") {
                override fun test(javaClass: JavaClass): Boolean {
                    return !javaClass.fullName.contains("$")
                }
            })
            .should().haveSimpleNameEndingWith("Controller")
            .check(baseClasses)
    }

    @Test
    fun repositoryClassesShouldEndWithRepository(){
        ArchRuleDefinition.classes().that().resideInAPackage("..repository")
            .and(object : DescribedPredicate<JavaClass>("are not Kotlin generated classes") {
                override fun test(javaClass: JavaClass): Boolean {
                    return !javaClass.fullName.contains("$")
                }
            }) //Tuve que hacer esta cochinada porque me tomaba una clase creada por kotlin y no podia filtarla
            .should().haveSimpleNameEndingWith("Repository")
            .check(baseClasses)
    }

    @Test
    fun controllerClassesShouldHaveSpringControllerAnnotation(){
        ArchRuleDefinition.classes().that().resideInAPackage("..controller")
            .and(object : DescribedPredicate<JavaClass>("are not Kotlin generated classes") {
                override fun test(javaClass: JavaClass): Boolean {
                    return !javaClass.fullName.contains("$")
                }
            })
            .should().beAnnotatedWith("org.springframework.web.bind.annotation.RestController")
            .check(baseClasses)
    }


}