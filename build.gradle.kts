plugins {
  java
  `maven-publish`
  id("org.jetbrains.kotlin.jvm")
}

repositories {
  // Use Maven Central for resolving dependencies.
  mavenCentral()
}

dependencies {
  constraints {
    // Define dependency versions as constraints
    implementation("org.apache.commons:commons-text:1.9")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
  }

  // Align versions of all Kotlin components
  implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

  // Use the Kotlin JDK 8 standard library.
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

  // Align versions of all Kotlin components
  implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

  // GSON for JSON file handling
  implementation("com.google.code.gson:gson:2.10.1")

  // Use JUnit Jupiter for testing.
  testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
}

tasks.named<Test>("test") {
  // Use JUnit Platform for unit tests.
  useJUnitPlatform()
}

publishing {
  publications {
    create<MavenPublication>("maven") {
      groupId = "com.github.mim1q"
      artifactId = "tada"
      version = "0.0.1"

      from(components["java"])
    }
  }
}