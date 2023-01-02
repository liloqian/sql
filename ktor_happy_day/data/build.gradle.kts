import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.0"
}

val commonRandomVersion: String by project
val gsonVersion: String by project
val ktor_version: String by project

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.apifan.common:common-random:$commonRandomVersion")
    implementation("com.google.code.gson:gson:$gsonVersion")
    implementation("io.ktor:ktor-client-cio:$ktor_version")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}