import org.gradle.api.JavaVersion

object Config {
    const val application_id = "leonov.ru.translator"
    const val compile_sdk = 29
    const val min_sdk = 21
    const val target_sdk = 29
    val java_version = JavaVersion.VERSION_1_8
}

object Releases {
    const val version_code = 1
    const val version_name = "1.0"
}

object Modules {
    const val app = ":app"
    const val core = ":core"
    const val model = ":model"
    const val repository = ":repository"
    const val utils = ":utils"
    // Features
    const val historyscreen = ":historyscreen"
}

object Versions {
    // Tools
    const val multidex = "1.0.3"

    // Design
    const val appcompat = "1.1.0"
    const val material = "1.1.0"
    const val swiperefreshlayout = "1.0.0"
    const val constraintlayout = "1.1.3"

    // Kotlin
    const val core = "1.2.0"
    const val stdlib = "1.3.71"

    // Coroutines
    const val coroutinesCore = "1.3.6"
    const val coroutinesAndroid = "1.3.0"

    // Retrofit
    const val retrofit = "2.8.1"
    const val converterGson = "2.7.1"
    const val interceptor = "4.4.0"
    const val adapterCoroutines = "0.9.2"

    // Koin
    const val koinAndroid = "2.1.5"
    const val koinViewModel = "2.1.5"

    // Glide
    const val glide = "4.11.0"
    const val glideCompiler = "4.11.0"

    // Picasso
    const val picasso = "2.5.2"

    // Room
    const val roomKtx = "2.2.5"
    const val runtime = "2.2.5"
    const val roomCompiler = "2.2.5"

    //Google Play
    const val googlePlayCore = "1.7.3"

    // Test
    const val jUnit = "4.13"
    const val mockk = "1.9.3"
    const val testCore = "1.1.1"
    const val kotlinxCoroutinesTest = "1.3.6"
    const val testKotlinxCoroutinesCore = "1.3.6"

    // AndroidTest
    const val androidJunit = "1.1.1"
    const val runner = "1.2.0"
    const val espressoCore = "3.2.0"
    const val mockkAndroid = "1.9.3"
    const val koinTest = "2.1.5"
}

object Tools {
    const val multidex = "com.android.support:multidex:${Versions.multidex}"
}

object Design {
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val swiperefreshlayout = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swiperefreshlayout}"
    const val constraintlayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}"
}

object Kotlin {
    const val core = "androidx.core:core-ktx:${Versions.core}"
    const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.stdlib}"
    const val coroutines_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesCore}"
    const val coroutines_android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesAndroid}"
}

object Retrofit {
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val converter_gson = "com.squareup.retrofit2:converter-gson:${Versions.converterGson}"
    const val adapter_coroutines =
        "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.adapterCoroutines}"
    const val logging_interceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.interceptor}"
}

object KoinImpl {
    const val koin_android = "org.koin:koin-androidx-scope:${Versions.koinAndroid}"
    const val koin_view_model = "org.koin:koin-androidx-viewmodel:${Versions.koinViewModel}"
}

object Glide {
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glide_compiler = "com.github.bumptech.glide:compiler:${Versions.glideCompiler}"
}

object Room {
    const val runtime = "androidx.room:room-runtime:${Versions.runtime}"
    const val compiler = "androidx.room:room-compiler:${Versions.roomCompiler}"
    const val room_ktx = "androidx.room:room-ktx:${Versions.roomKtx}"
}

object GooglePlay {
    const val googlePlayCore = "com.google.android.play:core:${Versions.googlePlayCore}"
}

object TestImpl {
    const val test_jUnit = "junit:junit:${Versions.jUnit}"
    const val mockk = "io.mockk:mockk:${Versions.mockk}"
    const val test_core = "android.arch.core:core-testing:${Versions.testCore}"
    const val kotlinx_coroutines_test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.kotlinxCoroutinesTest}"
    const val test_kotlinx_coroutines_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.testKotlinxCoroutinesCore}"
}

object AndroidTestImpl {
    const val android_jUnit = "androidx.test.ext:junit:${Versions.androidJunit}"
    const val runner = "androidx.test:runner:${Versions.runner}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
    const val mockk_android = "io.mockk:mockk-android:${Versions.mockkAndroid}"
    const val koin_test = "org.koin:koin-test:${Versions.koinTest}"
}