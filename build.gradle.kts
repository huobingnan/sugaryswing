import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.10"
}

group = "sugaryswing"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.reactivex.rxjava3:rxjava:3.1.0")

}



tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}