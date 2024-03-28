plugins {
    id("com.android.application")
    kotlin("android")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
}

android {
    namespace = ProjectConfig.applicationId
    compileSdk = ProjectConfig.compileSdk

    defaultConfig {
        applicationId = ProjectConfig.applicationId
        minSdk = ProjectConfig.minSdk
        targetSdk = ProjectConfig.targetSdk
        versionCode = ProjectConfig.versionCode
        versionName = ProjectConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Compose.version
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(AndroidX.core)
    implementation(AndroidX.lifecycleRuntime)
    implementation(Compose.activityCompose)
    implementation(Compose.viewModelCompose)
    implementation(Compose.ui)
    implementation(Compose.toolingPreview)
    implementation(Compose.material)
    implementation(Compose.navigation)
    implementation(Compose.hiltNavigationCompose)
    implementation(Room.roomRuntime)
    annotationProcessor(Room.roomCompiler)
    kapt(Room.roomCompiler)
    implementation(Room.coroutinesSupport)
    implementation(Retrofit.retrofit)
    implementation(Moshi.moshi)
    implementation(Retrofit.okHttp)
    implementation(Retrofit.okHttpLoggingInterceptor)
    implementation(Coroutines.coroutines)
    implementation(DaggerHilt.hilt)
    kapt(DaggerHilt.hiltCompiler)
    implementation(Coil.coil)
    implementation(Lottie.lottie)

    implementation(project(Modules.core))
    implementation(project(Modules.coreui))
    implementation(project(Modules.onboardingDomain))
    implementation(project(Modules.onboardingPresentation))
    implementation(project(Modules.loginData))
    implementation(project(Modules.loginDomain))
    implementation(project(Modules.loginPresentation))
    implementation(project(Modules.signupData))
    implementation(project(Modules.signupDomain))
    implementation(project(Modules.signupPresentation))
    implementation(project(Modules.productoverviewData))
    implementation(project(Modules.productoverviewDomain))
    implementation(project(Modules.productoverviewPresentation))
    implementation(project(Modules.productData))
    implementation(project(Modules.productDomain))
    implementation(project(Modules.productPresentation))
    implementation(project(Modules.shopoverviewData))
    implementation(project(Modules.shopoverviewDomain))
    implementation(project(Modules.shopoverviewPresentation))
    implementation(project(Modules.shopData))
    implementation(project(Modules.shopDomain))
    implementation(project(Modules.shopPresentation))

    testImplementation(Testing.junit)
    androidTestImplementation(Testing.junitExt)
    androidTestImplementation(Testing.espresso)
    androidTestImplementation(Compose.test)
    debugImplementation(Compose.toolingDebug)
    debugImplementation(Compose.testManifest)
}