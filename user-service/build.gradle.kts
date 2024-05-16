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
    implementation("io.quarkus:quarkus-jdbc-postgresql")
    implementation("io.quarkus:quarkus-reactive-pg-client")
    implementation("io.quarkus:quarkus-hibernate-reactive-panache")
    implementation("org.mapstruct:mapstruct:" + properties["mapstructVersion"])

    compileOnly("org.projectlombok:lombok:" + properties["lombokVersion"])

    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.quarkus:quarkus-junit5-mockito")
}


tasks.test {
    useJUnitPlatform()
}