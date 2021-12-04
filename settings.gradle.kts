import de.fayard.refreshVersions.core.StabilityLevel

pluginManagement {
    repositories { gradlePluginPortal() }
}

plugins {
    id("de.fayard.refreshVersions") version "0.23.0"
}

refreshVersions {
    extraArtifactVersionKeyRules(file("refreshVersions-extra-rules.txt"))

    rejectVersionIf {
        candidate.stabilityLevel.isLessStableThan(StabilityLevel.Milestone)
    }
}

