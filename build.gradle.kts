plugins {
    `java-library`
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "me.pan1st"
version = "1.0-SNAPSHOT"

val mcVersion = "1.20.1"

bukkit {
    main = "me.pan1st.invisibleitemframe.InvisibleItemFrame"
    name = project.name
    apiVersion = "1.16"
    authors = listOf("bread")
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.william278.net/releases")
}

dependencies {

    compileOnly("io.papermc.paper", "paper-api", "$mcVersion-R0.1-SNAPSHOT")
    implementation("io.papermc", "paperlib", "1.0.8")

    implementation(platform("cloud.commandframework:cloud-bom:1.8.3"))
    implementation("cloud.commandframework", "cloud-paper")
    implementation("cloud.commandframework", "cloud-minecraft-extras")
    implementation("cloud.commandframework", "cloud-annotations")
    annotationProcessor("cloud.commandframework:cloud-annotations:1.7.1")

    implementation("net.william278:annotaml:2.0.7")
}

tasks {
    assemble {
        dependsOn(shadowJar)
    }
    jar {
        archiveClassifier.set("noshade")
    }
    shadowJar {
        minimize()
        archiveClassifier.set(null as String?)
        archiveBaseName.set(project.name) // Use uppercase name for final jar

        val prefix = "${project.group}.${project.name.lowercase()}.lib"
        sequenceOf(
                "cloud.commandframework",
                "io.papermc.lib",
                "net.william278",
                "org.jetbrains",
                "org.intellij",
                "dev.dejvokep",
                "io.leangen"
        ).forEach { pkg ->
            relocate(pkg, "$prefix.$pkg")
        }

        dependencies {
            exclude(dependency("net.kyori:adventure-api"))
            exclude(dependency("org.jetbrains:annotations"))
        }
    }

}