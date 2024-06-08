plugins {
    id("java")
    id("io.quarkus") version "3.11.0.CR1"
}

group = "ru.expanse.account"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}