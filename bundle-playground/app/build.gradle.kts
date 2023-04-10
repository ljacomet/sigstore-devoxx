plugins {
    application
}

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

dependencies {
    implementation("dev.sigstore:sigstore-java:0.4.0")
}

application {
    mainClass.set("org.example.App")
}
