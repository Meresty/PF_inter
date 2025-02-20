plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.pf_inter"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.pf_inter"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation ("com.squareup.okhttp3:okhttp:4.9.3")
    implementation(libs.volley)
    implementation(libs.annotation)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    testImplementation(libs.junit)
    implementation ("com.squareup.okhttp3:okhttp:4.9.3")
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}