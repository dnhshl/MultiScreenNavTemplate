# Multi-Screen Navigation Template für Android

Dieses Projekt ist eine Vorlage für eine Android-App mit mehreren Bildschirmen, die Jetpack Compose verwendet. Es dient als Ausgangspunkt für Studierende, um die grundlegenden Konzepte der modernen Android-Entwicklung zu erlernen.

## Projektstruktur

Das Projekt ist in die folgenden Hauptordner und -dateien unterteilt:

- **`app/src/main/java/com/example/main`**: Enthält den gesamten Kotlin-Quellcode der App.
    - **`model`**: Beinhaltet die Datenklassen (`UiStates.kt`), das `MainViewModel.kt` und den `DatastoreManager.kt`. Dies ist die "Logik"-Schicht der App.
    - **`ui`**: Enthält alle UI-Komponenten (Composables).
        - **`navigation`**: Definiert die Navigationskomponenten wie `MyNavHost`, `MyTopBar`, `MyNavBar` und `MyMenu`.
        - **`screens`**: Enthält die einzelnen Bildschirme der App (z.B. `MainScreen.kt`, `Screen2.kt`).
        - **`theme`**: Definiert das visuelle Thema der App (Farben, Typografie).
    - **`MainActivity.kt`**: Der Einstiegspunkt der App.
- **`app/src/main/res`**: Enthält Ressourcen wie Bilder, Layouts und String-Definitionen (`values/strings.xml`).
- **`build.gradle.kts`**: Die Build-Skripte für das Projekt und das App-Modul, in denen Abhängigkeiten und Build-Konfigurationen deklariert werden.
- **`gradle/libs.versions.toml`**: Der [Version Catalog](https://developer.android.com/build/migrate-to-catalogs), der zur zentralen Verwaltung von Abhängigkeitsversionen verwendet wird.

## Schlüsselkonzepte

Diese Vorlage demonstriert die folgenden wichtigen Konzepte der Android-Entwicklung:

### ViewModel und State Management

Das **`MainViewModel.kt`** ist das Zentrum der Anwendungslogik. Es ist dafür verantwortlich, den Zustand der Benutzeroberfläche (UI) zu verwalten und auf Benutzerinteraktionen zu reagieren.

- **StateFlow**: Wir verwenden `StateFlow`, um den UI-Zustand zu speichern und an die UI zu übermitteln. Die UI "beobachtet" diese `StateFlows` und aktualisiert sich automatisch, wenn sich der Zustand ändert.
- **Zustandstypen**:
    - **UI-Zustand (`UiState`)**: Flüchtige Daten, die nur während der Laufzeit der App benötigt werden (z. B. ein Zähler).
    - **Persistenter UI-Zustand (`PersistantUiState`)**: Daten, die über App-Neustarts hinweg gespeichert werden sollen (z. B. Benutzereinstellungen).

### Jetpack Navigation

Die Navigation zwischen den verschiedenen Bildschirmen wird mit der **Jetpack Navigation Component** für Compose verwaltet.

- **`MyNavHost.kt`**: Definiert den "Navigationsgraphen", der jede Route (z. B. "main") mit einem Composable (z. B. `MainScreen`) verknüpft.
- **`NavController`**: Ein Objekt, das zur Auslösung von Navigationsaktionen verwendet wird (z. B. `navController.navigate("screen2")`).

### Jetpack DataStore

Für die dauerhafte Speicherung von einfachen Daten (wie Benutzereinstellungen) wird **Jetpack DataStore** verwendet.

- **`DatastoreManager.kt`**: Eine Hilfsklasse, die die Lese- und Schreibvorgänge für den DataStore kapselt und die Serialisierung und Deserialisierung von Daten mit Kotlinx Serialization übernimmt.
- **`PersistantUiState`**: Die Daten, die in diesem Zustand gehalten werden, werden automatisch im DataStore gespeichert und von dort geladen.
