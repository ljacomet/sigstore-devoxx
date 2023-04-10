
plugins {
    `java-library`
    `maven-publish`
    id("dev.sigstore.sign") version "0.4.1"
}

group = "org.example"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.guava:guava:31.1-jre")
}

publishing {
    publications {
        create<MavenPublication>("default") {
            from(components["java"])
        }
    }

    repositories {
        maven(url = uri("${rootProject.buildDir}/repo"))
    }
}

sigstoreSign {
    sigstoreJavaVersion.set("0.4.0")
}
