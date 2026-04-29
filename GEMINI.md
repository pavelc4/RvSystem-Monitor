# Project Context: RvSystem-Monitor

RvSystem-Monitor is an Android system monitoring application that combines a **Jetpack Compose** frontend with a high-performance **Rust** backend. It uses **JNI (Java Native Interface)** to bridge the two, allowing for efficient parsing of Linux kernel system files (`/proc`, `/sys`) while providing a modern Material 3 Expressive UI.

## Architecture

The project is split into two main components:

- **Frontend (`app/`)**:
    - **Framework**: Jetpack Compose (Kotlin).
    - **Architecture**: Clean Architecture (Domain, Data, UI layers).
    - **DI**: Dagger Hilt.
    - **Reactive Data**: StateFlow and Coroutines for lifecycle-aware streaming.
    - **UI**: Material 3 Expressive design with animated progress indicators.

- **Backend (`rust/`)**:
    - **Framework**: Rust.
    - **Organization**: Mirrors Linux kernel structure (`kernel/` for CPU, `mm/` for Memory Management).
    - **Functionality**: High-performance parsing of sysfs and procfs.
    - **Interface**: JNI functions mapped to Kotlin utility objects (`MemoryUtils`, `CpuUtils`). Optimized using **batch retrieval** (e.g., `getMemoryDataNative`, `getAllCoreFrequenciesNative`) to minimize context switching.

- **Services**:
    - **`SystemOverlayService`**: A foreground service that manages the floating system overlay, displaying live FPS and RAM metrics. It uses `Choreographer` for FPS and `MemoryUtils` for RAM.

## Building and Running

### Prerequisites
- **Android Studio**: Ladybug or newer.
- **Rust Toolchain**: [rustup.rs](https://rustup.rs/).
- **Android NDK**: Version `30.0.14904198` (as specified in `app/build.gradle.kts`).
- **cargo-ndk**: `cargo install cargo-ndk`.

### Key Commands

- **Build Everything (including Rust)**:
  ```bash
  ./gradlew assembleDebug
  ```
  *(The `preBuild` task depends on `buildRustLibraries`)*

- **Build Rust Libraries Individually**:
  ```bash
  ./gradlew :app:buildRustLibraries
  ```

- **Install and Run**:
  ```bash
  ./gradlew installDebug
  ```

- **Format Code (Spotless/ktlint)**:
  ```bash
  ./gradlew spotlessApply
  ```

- **Manual Rust Build (via CLI)**:
  ```bash
  cd rust
  cargo ndk -t armeabi-v7a -t arm64-v8a -o ../app/src/main/jniLibs build --release
  ```

## Project Structure

- `app/`: Android application module.
    - `src/main/java/com/rve/systemmonitor/`: Kotlin source code.
    - `src/main/jniLibs/`: Compiled Rust `.so` libraries (output of build process).
- `rust/`: Native monitoring backend.
    - `src/lib.rs`: JNI entry points.
    - `src/kernel/`: CPU monitoring logic.
    - `src/mm/`: Memory (RAM/ZRAM) monitoring logic.
- `gradle/libs.versions.toml`: Version catalog for dependencies.

## Development Conventions

- **Clean Architecture**: Maintain separation between data sources (Rust/JNI), domain logic (Repositories/Models), and the UI layer (ViewModels/Compose).
- **JNI Batching**: When adding new native metrics, prefer batching related data into a single JNI call (using `DoubleArray` or `jobjectArray`) to reduce overhead.
- **Lifecycle Awareness**: Ensure data streams in `MemoryRepositoryImpl` and `CpuRepositoryImpl` are stopped when screens are inactive or the app is in the background using `WhileSubscribed`.
- **UI Components**: Use `ScreenWrapper` for all top-level screens to ensure consistent transition effects and adaptive dimming.
- **Styling**: Use Material 3 Expressive. Custom icons are located in `app/src/main/res/drawable/`.
- **Debugging**: Monitor `MemoryRepository`, `CpuRepository`, and `BatteryUtils` logs in Logcat for data stream status.
- **Formatting**: Run `./gradlew spotlessApply` before committing Kotlin changes. Rust code should follow standard `cargo fmt` conventions.
