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

}



tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}