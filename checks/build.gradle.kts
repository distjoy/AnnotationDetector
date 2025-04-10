plugins {
    id("java-library")
    alias(libs.plugins.android.lint)
    kotlin("jvm")
}

java {
}

lint {
    htmlReport = true
    htmlOutput = file("lint-report.html")
    textReport = true
    absolutePaths = false
    ignoreTestSources = true
}

dependencies {

    testImplementation(libs.junit)


    compileOnly(libs.bundles.lint.api)
    testImplementation(libs.bundles.lint.tests)
    implementation(kotlin("stdlib-jdk8"))
}
repositories {
    mavenCentral()
}
kotlin {
    jvmToolchain(17)
}