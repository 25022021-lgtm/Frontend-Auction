plugins {
    java
    application
}

group = "app"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val junitVersion = "5.12.1"
val javafxVersion = "25.0.1"

// Detect current OS for JavaFX platform-specific jars
val osName = System.getProperty("os.name").lowercase()
val javafxPlatform = when {
    osName.contains("win") -> "win"
    osName.contains("mac") -> "mac"
    osName.contains("linux") -> "linux"
    else -> throw GradleException("Unsupported OS: $osName")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

application {
    mainModule.set("app.frontendauction")
    mainClass.set("app.frontendauction.Launcher")
}

dependencies {
    // JavaFX modules (platform-specific)
    implementation("org.openjfx:javafx-base:${javafxVersion}:${javafxPlatform}")
    implementation("org.openjfx:javafx-controls:${javafxVersion}:${javafxPlatform}")
    implementation("org.openjfx:javafx-fxml:${javafxVersion}:${javafxPlatform}")
    implementation("org.openjfx:javafx-graphics:${javafxVersion}:${javafxPlatform}")

    // Testing
    testImplementation("org.junit.jupiter:junit-jupiter-api:${junitVersion}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${junitVersion}")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
