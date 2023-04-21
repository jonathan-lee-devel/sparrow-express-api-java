plugins {
	java
	jacoco
	id("org.sonarqube") version "3.5.0.2730"
	id("org.springframework.boot") version "3.0.5"
	id("io.spring.dependency-management") version "1.1.0"
}

group = "io.jonathanlee"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-web")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
	finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
	dependsOn(tasks.test)
	reports {
		xml.required
		xml.isEnabled = true
	}
	sourceDirectories.setFrom(files(project.projectDir))
	executionData.setFrom(
			fileTree(project.projectDir) {
				setIncludes(setOf("**/**/*.exec", "**/**/*.ec"))
			}
	)
}

tasks.bootBuildImage {
	setProperty("imageName", "jonathanleedev/sparrow-express-api")
}

sonar {
	properties {
		property("sonar.host.url", "https://sonarcloud.io")
		property("sonar.organization", "io-jonathanlee")
		property("sonar.projectKey", "io-jonathanlee_sparrow-express-api")
		property("sonar.exclusions", "**/*Config.java,**/*Model.java,**/*Dto.java")
	}
}
