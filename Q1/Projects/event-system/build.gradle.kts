plugins {
    java
    application
}

group = "me.deniz"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // https://mvnrepository.com/artifact/com.mysql/mysql-connector-j
    implementation("com.mysql:mysql-connector-j:9.2.0")

    // https://mvnrepository.com/artifact/space.arim.dazzleconf/dazzleconf-ext-snakeyaml
    implementation("space.arim.dazzleconf:dazzleconf-ext-snakeyaml:1.3.0-M2")

    // https://mvnrepository.com/artifact/com.google.guava/guava
    implementation("com.google.guava:guava:33.4.0-jre")

    // https://mvnrepository.com/artifact/org.jetbrains/annotations
    implementation("org.jetbrains:annotations:26.0.2")

}