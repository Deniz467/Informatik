plugins {
    id("java")
    application
}

group = "me.deniz"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    gradlePluginPortal()
}

application {
    mainClass.set("me.deniz.vocab.Main")
}