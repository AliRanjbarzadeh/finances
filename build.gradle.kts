// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
	dependencies {
		classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.7")
	}
	repositories {
		google()
		mavenCentral()
	}
}
plugins {
	id("com.android.application") version "8.4.0" apply false
	id("org.jetbrains.kotlin.android") version "1.9.0" apply false
	id("com.google.dagger.hilt.android") version "2.49" apply false
}