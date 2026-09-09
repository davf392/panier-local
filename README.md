# Panier Local

Panier Local est une application mobile et bureau dédiée à la gestion et à la logistique opérationnelle des AMAP (Associations pour le Maintien d’une Agriculture Paysanne).

L'objectif est d'optimiser le temps passé par les salariés et les bénévoles lors des permanences de distribution, tout en offrant une interface fluide pour le suivi des adhérents.

## 🚀 Fonctionnalités du Prototype

Le prototype actuel implémente les fonctionnalités suivantes :

- 📊 **Tableau de bord : Visualisation en direct du point de collecte actif (alertes, ratio paniers récupérés/attendus, bénévoles/astreintes).
- 🧺 **Consultation des Paniers** : Affichage détaillé du contenu des différentes formules de paniers selon le lieu de distribution.
- 👥 **Suivi des Présences Adhérents** : Liste des passages adhérents, pointage en temps réel et outils de relance.
- ⚖️ **Simulateur d'Échanges** : Calcul des équivalences poids/valeur entre produits lors des échanges sur place.

## 🛠️ Architecture Technique

- **Langage** : Kotlin Multiplatform (KMP)
- **UI** : Compose Multiplatform
- **Modèle** : Clean Architecture orientée Features, utilisant les Coroutines et Flows pour la réactivité.

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
