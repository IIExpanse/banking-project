plugins {
    id("java")
    id("io.quarkus") version "3.11.0.CR1"
}

group = "ru.expanse"
version = "1.0.0"

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
    implementation("io.quarkus:quarkus-grpc")
    implementation("org.mapstruct:mapstruct:" + properties["mapstructVersion"])
    implementation("org.projectlombok:lombok-mapstruct-binding:" + properties["lombokMapstructBindingVersion"])

    compileOnly("org.projectlombok:lombok:" + properties["lombokVersion"])
    annotationProcessor("org.projectlombok:lombok:" + properties["lombokVersion"])
    annotationProcessor("org.mapstruct:mapstruct-processor:" + properties["mapstructVersion"])

    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.quarkus:quarkus-junit5-mockito")
    testCompileOnly("org.projectlombok:lombok:" + properties["lombokVersion"])
}


tasks.test {
    useJUnitPlatform()
}

tasks.quarkusDev {
    environmentVariables.set(run {
        val map: MutableMap<String, String> = HashMap()
        file("env.").readLines().forEach { line ->
            run {
                val arr = line.split("=")
                map[arr[0]] = arr[1]
            }
        }
        return@run map
    })
}