import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val mysqlVersion: String by project
val commonRandom: String by project

plugins {
    kotlin("jvm") version "1.7.20"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("redis.clients:jedis:$mysqlVersion")
    implementation("com.apifan.common:common-random:$commonRandom")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}