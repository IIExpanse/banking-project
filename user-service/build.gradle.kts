plugins {
    id("java")

}

group = "ru.expanse"
version = "unspecified"

repositories {
    mavenCentral()
}

val properties: Map<String, *> = project.properties

dependencies {
    implementation(platform("io.quarkus.platform:quarkus-bom:" + properties["quarkusPlatformVersion"]))
    implementation("io.quarkus:quarkus-arc")
    implementation("io.quarkus:quarkus-liquibase")
    implementation("io.quarkus:quarkus-reactive-pg-client")

    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.quarkus:quarkus-junit5-mockito")
}

tasks.test {
    useJUnitPlatform()
}