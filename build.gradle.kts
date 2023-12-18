plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.telegram:telegrambots:6.8.0")
    implementation("org.json:json:20201115")
    implementation("com.github.cliftonlabs:json-simple:3.1.0")
}

tasks.test {
    useJUnitPlatform()
}