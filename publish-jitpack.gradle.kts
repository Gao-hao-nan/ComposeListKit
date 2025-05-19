afterEvaluate {
    extensions.configure<PublishingExtension>("publishing") {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                groupId = "com.github.Gao-hao-nan"
                artifactId = "ComposeListKit"
                version = "1.0.0-beta01"
            }
        }
    }
}
