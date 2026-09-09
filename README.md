# Panier Local [![Kotlin Version](https://img.shields.io/badge/kotlin-2.2.0-blue.svg)](https://kotlinlang.org) [![API](https://img.shields.io/badge/API-35%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=35) [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

Panier Local is an application designed to simplify AMAP (Associations pour le Maintien d’une Agriculture Paysanne) management and logistics by connecting members, producers, and volunteers through a simple, intuitive interface.

## ✨ Features

- 📊 **Dashboard** : View live collection point status and volunteer activity.
- 🧺 **Basket Consultation** : Easily browse basket contents by location.
- 👤 **Attendance Tracking** : Manage member check-ins and follow-ups in real-time.
- ⚖️ **Exchange Simulator** : Instantly calculate product weight and value equivalences.

## 🛠️ Technical Architecture

- **Language** : Kotlin Multiplatform (KMP)
- **UI** : Compose Multiplatform
- **Model** : Clean Architecture using Coroutines and Flows for reactivity.

## 💻 Launch Instructions

The project uses Gradle for build management. 

Ensure you have a compatible JDK installed (e.g., JDK 17+).

- **Run on Android** 📱
  ```bash
  ./gradlew :composeApp:installDebug
  ```

- **Run on Desktop (JVM)** 🖥️
  ```bash
  ./gradlew :composeApp:run
  ```

## 🤝 Contributing

Do you want to contribute to the code ? Here's how :

1. Fork the repository

2. Create a new branch ([via GitHub](https://help.github.com/articles/creating-and-deleting-branches-within-your-repository/) or locally):
   ```bash
   git checkout -b name-of-your-branch
   ```
   
3. Commit your changes
   ```bash
   git commit -m 'some message'
   ```

4. Push to the branch
   ```bash
   git push origin name-of-your-branch
   ```

5. [Submit a Pull Request](https://github.com/davf392/panier-local/compare)

## 🌟 Star the project
If you find this project useful, please consider giving it a star on GitHub ! 

It helps others find the project and encourages further development.

## 📄 License
This project is licensed under the Apache License 2.0. See the [LICENSE.md](LICENSE.md) file for more details.
