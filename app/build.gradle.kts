plugins {
	id("com.android.application")
	id("org.jetbrains.kotlin.android")
	id("androidx.navigation.safeargs.kotlin")
	id("kotlin-parcelize")
	id("com.google.dagger.hilt.android")
	id("org.jetbrains.kotlin.kapt")
}

android {
	namespace = "ir.aliranjbarzadeh.finances"
	compileSdk = 34

	defaultConfig {
		applicationId = "ir.aliranjbarzadeh.finances"
		minSdk = 24
		targetSdk = 34
		versionCode = 1
		versionName = "1.0"
		multiDexEnabled = true

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

		resourceConfigurations.clear()
		resourceConfigurations.add("fa")
	}

	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
		dataBinding = true
	}
}

dependencies {
	implementation("androidx.core:core-ktx:1.12.0")
	implementation("androidx.appcompat:appcompat:1.6.1")
	implementation("com.google.android.material:material:1.11.0")
	implementation("androidx.constraintlayout:constraintlayout:2.1.4")
	implementation("androidx.viewpager2:viewpager2:1.0.0")

	// Room
	implementation("androidx.room:room-runtime:2.6.1")
	//noinspection KaptUsageInsteadOfKsp
	kapt("androidx.room:room-compiler:2.6.1")
	implementation("androidx.room:room-ktx:2.6.1")

	// Network
	implementation("com.squareup.retrofit2:retrofit:2.9.0")
	implementation("com.squareup.retrofit2:converter-gson:2.9.0")
	implementation("com.squareup.okhttp3:okhttp:4.10.0")

	// Coroutines
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
	implementation("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")

	// Multidex
	implementation("androidx.multidex:multidex:2.0.1")

	// Navigation
	implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
	implementation("androidx.navigation:navigation-ui-ktx:2.7.7")

	// Hilt
	implementation("com.google.dagger:hilt-android:2.49")
	kapt("com.google.dagger:hilt-android-compiler:2.49")

	// Calligraphy
	implementation("io.github.inflationx:calligraphy3:3.1.1")
	implementation("io.github.inflationx:viewpump:2.0.3")

	// Hawk
	implementation("com.orhanobut:hawk:2.0.1")

	// Sizes
	implementation("com.intuit.ssp:ssp-android:1.1.0")
	implementation("com.intuit.sdp:sdp-android:1.1.0")

	// Lottie
	implementation("com.airbnb.android:lottie:5.2.0")

	// Additional
	implementation("com.github.samanzamani:PersianDate:1.7.1")
	implementation("io.github.ParkSangGwon:tedkeyboardobserver:1.0.1")
	implementation("androidx.core:core-splashscreen:1.0.1")

	testImplementation("junit:junit:4.13.2")
	androidTestImplementation("androidx.test.ext:junit:1.1.5")
	androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}