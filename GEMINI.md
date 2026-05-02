# RvSystem Monitor - Project Context

This file provides the necessary context and instructions for AI agents working on the RvSystem Monitor project.

## Project Overview

**RvSystem Monitor** is a high-performance system monitoring solution for Android. It leverages **Jetpack Compose** for a modern, fluid user interface and **Rust** for efficient, low-level hardware data parsing.

### Key Technologies
- **Frontend**: Kotlin, Jetpack Compose (Material 3 Expressive), Dagger Hilt (DI), Coroutines/StateFlow.
- **Backend**: Rust (via JNI), direct `/proc` and `/sys` file parsing.
- **Build System**: Gradle Kotlin DSL with integration for Cargo NDK.
- **Libraries**: Backdrop (UI), DataStore (Preferences), Splashscreen.

### Architecture
The project follows **Clean Architecture** principles:
- **`app/src/main/java/com/rve/systemmonitor/ui`**: UI components, ViewModels, and Navigation.
- **`app/src/main/java/com/rve/systemmonitor/domain`**: Business logic and interfaces.
- **`app/src/main/java/com/rve/systemmonitor/data`**: Implementation of repositories and data sources.
- **`app/src/main/java/com/rve/systemmonitor/utils`**: JNI bridge declarations and utility classes.
- **`rust/src`**: Native implementation of hardware monitoring (mirrors Linux kernel structure: `kernel/` for CPU, `mm/` for Memory).

## Building and Running

### Prerequisites
- **Android Studio** (Ladybug or newer)
- **Rust Toolchain** ([rustup.rs](https://rustup.rs/))
- **Android NDK** (Version `30.0.14904198` specified in `app/build.gradle.kts`)
- **cargo-ndk**: `cargo install cargo-ndk`

### Key Commands
- **Build Native Libraries**:
  ```bash
  ./gradlew :app:buildRustLibraries
  ```
  This task compiles Rust code for `arm64-v8a` and `armeabi-v7a` and places the `.so` files in `app/src/main/jniLibs`.
- **Assemble Debug APK**:
  ```bash
  ./gradlew assembleDebug
  ```
- **Apply Code Formatting**:
  ```bash
  ./gradlew spotlessApply
  ```
  ```bash
  cd rust && cargo fmt
  ```

## Development Conventions

### JNI Bridge
- **Batch Data Retrieval**: To minimize JNI overhead, prefer retrieving data in batches (e.g., `getMemoryDataNative` returns a `doubleArray` containing all RAM and ZRAM stats).
- **Naming Convention**: JNI functions in Rust must follow the `Java_package_name_ClassName_functionName` pattern and use `#![allow(non_snake_case)]`.

### UI/UX
- **Material 3 Expressive**: Use modern Material 3 components and adaptive layouts.
- **Theme Support**: Dynamic Light/Dark mode and customizable haptic feedback/vibration intensity are handled via `RvSystemMonitorTheme`.
- **Performance**: Ensure UI stays responsive even during high-frequency system updates.

### Native Backend (Rust)
- **Kernel Parity**: Organize native code to reflect the Linux kernel structure it parses.
- **Error Handling**: Native code should be robust against missing or inaccessible `/proc` or `/sys` files.

## Important Files
- `app/build.gradle.kts`: Main Android build configuration and NDK integration.
- `gradle/libs.versions.toml`: Centralized dependency management.
- `rust/src/lib.rs`: JNI entry points for the native backend.
- `app/src/main/java/com/rve/systemmonitor/MainActivity.kt`: Entry point for the Android application.
