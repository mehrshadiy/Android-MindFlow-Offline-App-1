# MindFlow (Android - Offline Version)

MindFlow is a mental health tracking app built with **Kotlin + Jetpack Compose**, using **MVVM + Clean Architecture**. This version stores all data locally using **Room Database**, fully offline.

## Features
- Daily mood recording (Excellent → Very Bad) with optional notes
- List of recorded moods
- Simple charts replaced with mood lists
- Relaxation exercises management
- In-app Admin mode (PIN protected) to manage exercises and view user data
- Fully offline, no Firebase required

## Architecture
- MVVM
- Repository Pattern
- Room for local database (Mood & Exercises)
- Jetpack Compose for UI

## Database
- **Mood Table**: id, moodType, note, date
- **Exercise Table**: id, title, description, type

## Setup
1. Clone repo
2. Open in Android Studio
3. Build & Run (no Firebase needed)