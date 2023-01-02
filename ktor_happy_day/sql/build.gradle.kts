import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.0"
}

val commonRandomVersion: String by project
val jediVersion: String by project

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("redis.clients:jedis:$jediVersion")
    implementation(project(mapOf("path" to ":data")))
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}