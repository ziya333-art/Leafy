import java.util.Properties

plugins {
    id("com.ujizin.android-application")
}

android {
    namespace = "com.ujizin.leafy"

    defaultConfig {
        versionCode = 3
        versionName = "1.1.0"
    }

    signingConfigs {
        getByName("release") {
            val keystorePropertiesFile = rootProject.file("keystore.properties")
            if (keystorePropertiesFile.exists()) {
                val keystoreProperties = Properties().apply {
                    load(keystorePropertiesFile.inputStream())
                }
                storeFile = rootProject.file(keystoreProperties.getProperty("storeFile"))
                storePassword = keystoreProperties.getProperty("storePassword")
                keyAlias = keystoreProperties.getProperty("keyAlias")
                keyPassword = keystoreProperties.getProperty("keyPassword")
            }
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
        }
    }
}

dependencies {
    implementation(libs.androidx.core.splashscreen)

    implementation(projects.features.home)
    implementation(projects.features.search)
    implementation(projects.features.alarm)
    implementation(projects.features.camera)
    implementation(projects.features.publish)
    implementation(projects.features.about)
    implementation(projects.features.preferences)
    implementation(projects.features.tasks)
    implementation(projects.features.plant)

    implementation(projects.domain)
    implementation(projects.core.local)
    implementation(projects.core.weather)

    implementation(projects.core.ui)
    implementation(projects.core.themes)
    implementation(projects.core.navigation)

    androidTestImplementation(libs.bundles.test)
    androidTestImplementation(libs.bundles.androidx.test)
}
