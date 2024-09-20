plugins {
	alias(libs.plugins.android.application)
	alias(libs.plugins.jetbrains.kotlin.android)
	id("kotlin-kapt")
}

android {
	namespace = "com.example.shoppinglist"
	compileSdk = 34

	defaultConfig {
		applicationId = "com.example.shoppinglist"
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
	buildFeatures {
		dataBinding = true
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_1_8
		targetCompatibility = JavaVersion.VERSION_1_8
	}
	kotlinOptions {
		jvmTarget = "1.8"
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

	// Coroutines Core
	implementation(libs.kotlinx.coroutines.core)

	// Coroutines Android
	implementation(libs.kotlinx.coroutines.android)

	// Room runtime
	implementation(libs.androidx.room.runtime)

	// Room compiler (Annotation Processor)
	kapt(libs.androidx.room.compiler)

	// Room Kotlin Extensions and Coroutines support
	implementation(libs.androidx.room.ktx)

	// Lifecycle LiveData and Transformations support
	implementation(libs.androidx.lifecycle.livedata.ktx)
	
	// Lifecycle ViewModel
	implementation(libs.androidx.lifecycle.viewmodel.ktx)

	// Lifecycle Runtime with Coroutines support
	implementation(libs.androidx.lifecycle.runtime.ktx)

	// Dagger dependencies
	implementation(libs.dagger)
	kapt(libs.dagger.compiler)
}