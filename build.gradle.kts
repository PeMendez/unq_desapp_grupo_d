plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.3.3"
	id("io.spring.dependency-management") version "1.1.6"
	kotlin("plugin.jpa") version "1.9.25"
	id("org.sonarqube") version "5.1.0.4882"
}


group = "com.desapp"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

val mockitoVersion = "3.10.0"
val springBootStarterVersion = "3.2.4"
val springSecurityVersion = "6.2.3"
val validationApiVersion = "2.0.1.Final"
val openapiVersion = "2.3.0"


dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	runtimeOnly("com.h2database:h2")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation("org.springframework.boot:spring-boot-starter-security:${springBootStarterVersion}")
	implementation("org.springframework.security:spring-security-core:${springSecurityVersion}")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")
	testImplementation("org.mockito:mockito-core:${mockitoVersion}")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:${openapiVersion}")
	implementation("javax.validation:validation-api:${validationApiVersion}")
	testImplementation("org.springframework.security:spring-security-test")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("com.tngtech.archunit:archunit:1.3.0")
	developmentOnly("org.springframework.boot:spring-boot-devtools")


}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

sonar {
	properties {
		property("sonar.projectKey", "unq_desapp_grupo_d")
		property("sonar.projectName", "unq_desapp_grupo_d")
	}
}