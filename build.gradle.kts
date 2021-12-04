import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// Note: AWS supports only JDK 11 for Lambdas so far
val targetJdk = JavaVersion.VERSION_17

plugins {
    kotlin("jvm") version "1.6.0"
    kotlin("plugin.serialization") version "1.6.0"
}

tasks.named<Wrapper>("wrapper") {
    gradleVersion = "7.3"
    distributionType = Wrapper.DistributionType.ALL
}

dependencies {
    implementation(KotlinX.serialization.json)
    testImplementation(Testing.kotest.runner.junit5)
    testImplementation(Testing.kotest.property)
}

allprojects {
    repositories {
        mavenCentral()
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = targetJdk.toString()
        }
    }

    plugins.withType<JavaPlugin> {
        extensions.configure<JavaPluginExtension> {
            sourceCompatibility = targetJdk
            targetCompatibility = targetJdk
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}
