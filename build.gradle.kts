plugins {
	id("org.springframework.boot") version "3.3.3"
	id("io.spring.dependency-management") version "1.1.6"
	id("org.sonarqube") version "5.1.0.4882"
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	kotlin("plugin.jpa") version "1.9.25"
	jacoco
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
val validationApiVersion = "2.0.1.Final"
val openapiVersion = "2.3.0"
val archunitVersion = "1.3.0"
val webmvcVersion = "2.3.0"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.springframework.security:spring-security-core")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:${webmvcVersion}")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:${openapiVersion}")
	implementation("javax.validation:validation-api:${validationApiVersion}")
	implementation("org.springframework.boot:spring-boot-starter-security")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testImplementation("org.mockito:mockito-core:${mockitoVersion}")
	testImplementation("org.springframework.security:spring-security-test")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("com.tngtech.archunit:archunit:${archunitVersion}")

	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	runtimeOnly("com.h2database:h2")

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
		property("sonar.token", "sqp_acb1ec07d7fc14467b173248451bb1be2ee640f0")
	}
}

jacoco {
	toolVersion = "0.8.11"
	reportsDirectory = layout.buildDirectory.dir("jacocoReportDir")
}

tasks.jacocoTestReport{
	dependsOn(tasks.test)
	reports {
		xml.required=true
		html.required=true
		html.outputLocation = layout.buildDirectory.dir("jacocoHtml")
	}
}

tasks.test {
	finalizedBy(tasks.jacocoTestReport)
}