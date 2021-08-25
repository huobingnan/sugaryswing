import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.10"
    `maven-publish`
}

group = "com.github.huobingnan"

version = "0.0.4"

repositories {
    mavenCentral()
}


dependencies {

}


tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.github.huobingnan"
            artifactId = "sugaryswing"
            version = "0.0.4"
            from(components["kotlin"])
        }
    }
}