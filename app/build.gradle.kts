plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.example.todoapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.todoapp"
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)
    implementation(libs.androidx.navigation.ui.ktx)

    implementation(libs.hilt.android)
    implementation(libs.androidx.recyclerview)
    kapt(libs.hilt.android.compiler)

    // Room
    implementation(libs.androidx.room.runtime)

    implementation(libs.kotlinx.coroutines.android)
    kapt("androidx.room:room-compiler:2.5.2")

 // Asegúrate de que la versión esté actualizada
    implementation("androidx.room:room-ktx:2.5.0") // Añade esta línea
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0") // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0") // Coroutines para Android
}

kapt {
    correctErrorTypes = true
}