import org.gradle.kotlin.dsl.`maven-publish`

plugins {
  java
  `maven-publish`
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