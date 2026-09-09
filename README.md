# Panier Local [![Kotlin Version](https://img.shields.io/badge/kotlin-2.2.0-blue.svg)](https://kotlinlang.org) [![API](https://img.shields.io/badge/API-35%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=35) [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

Cette application a été conçue pour faciliter la gestion, la communication et la logistique des AMAP (Associations pour le Maintien d’une Agriculture Paysanne) en mettant en relation adhérents, producteurs et bénévoles.
Elle vise à réduire les frictions administratives et à favoriser l’autonomie via une interface simple et intuitive.

## Fonctionnalités du Prototype

Le prototype actuel implémente les fonctionnalités suivantes :

- **Tableau de bord** : Visualisation en direct du point de collecte actif (alertes, ratio paniers récupérés/attendus, bénévoles/astreintes).
- **Consultation des Paniers** : Affichage détaillé du contenu des différentes formules de paniers selon le lieu de distribution.
- **Suivi des Présences Adhérents** : Liste des passages adhérents, pointage en temps réel et outils de relance.
- **Simulateur d'Échanges** : Calcul des équivalences poids/valeur entre produits lors des échanges sur place.

## 🛠️ Architecture Technique

- **Langage** : Kotlin Multiplatform (KMP)
- **UI** : Compose Multiplatform
- **Modèle** : Clean Architecture utilisant les Coroutines et Flows pour la réactivité.

## 💻 Instructions de lancement

Le projet utilise Gradle pour la gestion des builds. 
Assurez-vous d'avoir un JDK compatible installé (ex: JDK 17+).

- **Lancer sur Android** :
  ```bash
  ./gradlew :composeApp:installDebug
  ```
- **Lancer sur Desktop (JVM)** :
  ```bash
  ./gradlew :composeApp:run
  ```

---

## 🤝 Contributing

### Voulez-vous contribuer au code ?

1. [Fork PanierLocal](https://github.com/davf392/panier-local/)
2. Créez une nouvelle branche ([via GitHub](https://help.github.com/articles/creating-and-deleting-branches-within-your-repository/) ou localement :

   ```bash
   git checkout -b nom-de-votre-branche develop
   ```
3. [Soumettez une Pull Request](https://github.com/davf392/panier-local/compare)
