plugins {
    id("com.android.application")
}

android {
    namespace = "com.mkvsk.warehousewizard"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.mkvsk.warehousewizard"
        minSdk = 27
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    //    local db
    val roomVersion = "2.6.1"
    implementation("androidx.room:room-runtime:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")
    // To use Kotlin annotation processing tool (kapt)
    /*kapt "androidx.room:room-compiler:$room_version"*/
    // To use Kotlin Symbol Processing (KSP)
//    ksp "androidx.room:room-compiler:$room_version"
    implementation("androidx.room:room-runtime:$roomVersion")
//    kapt("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$roomVersion")
//    implementation("androidx.room:room-paging:+ $roomVersion")
//    charts
//    https://android-er.blogspot.com/2016/07/mpandroidchart-powerful-android-chart.html
//    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
//    country picker
    implementation("com.hbb20:ccp:2.7.3")
//    base
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.navigation:navigation-fragment:2.7.6")
    implementation("androidx.navigation:navigation-ui:2.7.6")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}