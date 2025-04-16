plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.project.recipiebuddy"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.project.recipiebuddy"
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
//implementation(files("libs/ojdbc8.jar"))
    //    implementation(files("libs\\ojdbc8.jar"))
    val fragment_version = "1.8.6"

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation ("androidx.recyclerview:recyclerview:1.4.0")
    // For control over item selection of both touch and mouse driven selection
//    implementation ("androidx.recyclerview:recyclerview-selection:1.1.0")
    implementation ("androidx.cardview:cardview:1.0.0")
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.facebook.shimmer:shimmer:0.5.0")




    // Java language implementation
    implementation ("androidx.fragment:fragment:$fragment_version")
}