plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.mv.cv_k_v1"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.mv.cv_k_v1"
        minSdk = 29
        targetSdk = 35
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

    buildFeatures{
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

    //DEPENDENCIAS DE FIREBASE
    //Platform
    implementation(platform(libs.firebase.bom))
    //Analytics
    implementation(libs.firebase.analytics)
    // Firebase Authentication
    implementation(libs.firebase.auth.ktx)
    // Cloud Firestore
    implementation(libs.firebase.firestore.ktx)
    // Cloud Storage for Firebase
    implementation(libs.firebase.storage.ktx)
    // Realtime Database
    implementation(libs.firebase.database.ktx)
    //Google Play Services
    implementation(libs.play.services.auth)
}