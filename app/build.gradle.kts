import java.io.FileInputStream
import java.util.Properties

plugins {
	id("com.android.application")
	id("org.jetbrains.kotlin.android")
	id("androidx.navigation.safeargs.kotlin")
	id("kotlin-parcelize")
	id("com.google.dagger.hilt.android")
//	id("org.jetbrains.kotlin.kapt")
	id("com.google.devtools.ksp")
}

// keystore.properties file, in the rootProject folder.
val keystorePropertiesFile = rootProject.file("keystore.properties")

// Initialize a new Properties() object called keystoreProperties.
val keystoreProperties = Properties()

// Load your keystore.properties file into the keystoreProperties object.
keystoreProperties.load(FileInputStream(keystorePropertiesFile))

android {
	signingConfigs {
		create("release") {
			storeFile = file(keystoreProperties["storeFile"] as String)
			storePassword = keystoreProperties["storePassword"] as String
			keyPassword = keystoreProperties["keyPassword"] as String
			keyAlias = keystoreProperties["keyAlias"] as String
		}
	}
	namespace = "ir.aliranjbarzadeh.finances"
	compileSdk = 36

	defaultConfig {
		applicationId = "ir.aliranjbarzadeh.finances"
		minSdk = 24
		targetSdk = 36
		versionCode = 4
		versionName = "1.0.4"
		multiDexEnabled = true

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}

	ksp {
		arg("room.schemaLocation", "$projectDir/schemas")
	}

	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
			signingConfig = signingConfigs.getByName("release")
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_17
		targetCompatibility = JavaVersion.VERSION_17
	}
	kotlin {
		jvmToolchain(17)
	}
	buildFeatures {
		dataBinding = true
	}
}

dependencies {
	implementation("androidx.core:core-ktx:1.16.0")
	implementation("androidx.appcompat:appcompat:1.7.1")
	implementation("com.google.android.material:material:1.12.0")
	implementation("androidx.constraintlayout:constraintlayout:2.2.1")
	implementation("androidx.viewpager2:viewpager2:1.1.0")

	// Room
	implementation("androidx.room:room-runtime:2.7.2")
	testImplementation("org.junit.jupiter:junit-jupiter:5.13.3")
	ksp("androidx.room:room-compiler:2.7.2")
	implementation("androidx.room:room-ktx:2.7.2")

	// Network
	implementation("com.squareup.retrofit2:retrofit:3.0.0")
	implementation("com.squareup.retrofit2:converter-gson:3.0.0")
	implementation("com.squareup.okhttp3:okhttp:5.0.0")

	// Coroutines
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2")
	implementation("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")

	// Multidex
	implementation("androidx.multidex:multidex:2.0.1")

	// Navigation
	implementation("androidx.navigation:navigation-fragment-ktx:2.9.1")
	implementation("androidx.navigation:navigation-ui-ktx:2.9.1")

	// Hilt
	implementation("com.google.dagger:hilt-android:2.56.2")
	ksp("com.google.dagger:hilt-android-compiler:2.56.2")

	// Calligraphy
	implementation("io.github.inflationx:calligraphy3:3.1.1")
	implementation("io.github.inflationx:viewpump:2.1.1")

	// Hawk
	implementation("com.orhanobut:hawk:2.0.1")

	// Sizes
	implementation("com.intuit.ssp:ssp-android:1.1.1")
	implementation("com.intuit.sdp:sdp-android:1.1.1")

	// Lottie
	implementation("com.airbnb.android:lottie:6.6.7")

	// Additional
	implementation("androidx.core:core-splashscreen:1.0.1")
	implementation("com.github.samanzamani:PersianDate:1.7.1")
	implementation("io.github.ParkSangGwon:tedkeyboardobserver:1.0.1")
	implementation("com.adivery:sdk:4.8.3")
	implementation("ir.tapsell.plus:tapsell-plus-sdk-android:2.3.2")

	testImplementation("junit:junit:4.13.2")
	androidTestImplementation("androidx.test.ext:junit:1.2.1")
	androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
}