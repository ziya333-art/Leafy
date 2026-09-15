plugins {
    id("com.ujizin.android-library")
}

android {
    namespace = "com.ujizin.leafy.core.weather"
}

dependencies {
    implementation(libs.open.meteo)

    implementation(projects.domain)
    implementation(projects.core.local)
}
