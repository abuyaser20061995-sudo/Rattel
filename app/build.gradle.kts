plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.rattel.app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.rattel.app"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
    jvmTarget = "17"
    freeCompilerArgs += "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api"
}

dependencies {
    val composeBom = platform("androidx.compose:compose-bom:2023.10.01")
    implementation(composeBom)
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    implementation("com.google.dagger:hilt-android:2.49")
    ksp("com.google.dagger:hilt-compiler:2.49")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
    implementation("androidx.navigation:navigation-compose:2.7.5")
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    ksp("androidx.room:room-compiler:$room_version")
    val media3_version = "1.2.0"
    implementation("androidx.media3:media3-exoplayer:$media3_version")
    implementation("androidx.media3:media3-session:$media3_version")
    implementation("androidx.work:work-runtime-ktx:2.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")
    implementation("com.google.accompanist:accompanist-permissions:0.32.0")
 
    // مكتبات محرك الذكاء الاصطناعي الصوتي (Vosk)
   // implementation("net.java.dev.jna:jna:5.13.0@aar")
   // implementation("com.alphacep:vosk-android:0.3.32@aar")

}
