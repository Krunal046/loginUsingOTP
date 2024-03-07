plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")

}

android {
    namespace = "com.example.demo"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.demo"
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
    //sdp
    implementation("com.intuit.sdp:sdp-android:1.1.0")
    //ssp
    implementation("com.intuit.ssp:ssp-android:1.1.0")
    //material design
    implementation("com.google.android.material:material:1.3.0-alpha03")
    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.5.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")
    //Coroutine
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9")
    //ViewModel and LiveData
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.4.0")
    //Glide
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    //Room
    implementation ("androidx.room:room-runtime:2.4.3")
    annotationProcessor( "android.arch.persistence.room:compiler:1.0.0")
    kapt ("androidx.room:room-compiler:2.4.3")

}
