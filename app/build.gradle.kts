plugins {
    id("com.android.application")
    id("com.google.devtools.ksp") version "1.8.10-1.0.9" apply (true)
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17

        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    //    rx
    implementation("io.reactivex.rxjava2:rxjava:2.2.9")
    implementation("io.reactivex.rxjava2:rxandroid:2.1.0")
    implementation("android.arch.lifecycle:runtime:1.1.1")
    implementation("android.arch.lifecycle:livedata:1.1.1")
    implementation("android.arch.lifecycle:reactivestreams:1.1.1")
    implementation("android.arch.persistence.room:runtime:1.1.1")
    implementation("android.arch.persistence.room:rxjava2:1.1.1")
//    androidTestImplementation ("com.android.support.test:rules:1.0.2")
    androidTestImplementation("android.arch.core:core-testing:1.1.1")
//    androidTestImplementation ("com.android.support:support-compat:28.0.0")
    androidTestImplementation("android.arch.lifecycle:common:1.1.1")
//    implementation("android.arch.lifecycle:extensions:1.1.1")
//
//  images
    implementation("com.github.bumptech.glide:glide:4.16.0")
//    generate random data
    implementation("com.github.javafaker:javafaker:1.0.2")
//  rv libraries
    implementation("androidx.recyclerview:recyclerview:1.3.2")
//  local db
    implementation("androidx.room:room-runtime:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")
    // To use Kotlin annotation processing tool (kapt)
    /*kapt "androidx.room:room-compiler:$room_version"*/
    // To use Kotlin Symbol Processing (KSP)
    implementation("androidx.room:room-ktx:2.6.1")
//    implementation("androidx.room:room-paging:+ $roomVersion")
//    charts
//    https://android-er.blogspot.com/2016/07/mpandroidchart-powerful-android-chart.html
//    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
//    base
    implementation ("androidx.navigation:navigation-fragment-ktx:2.7.6")
    implementation("android.arch.lifecycle:viewmodel:1.1.1")
    implementation("android.arch.lifecycle:extensions:1.1.1")
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