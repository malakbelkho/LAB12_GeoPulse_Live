# 🌍 GeoPulse Live – LAB 12

Application Android de **localisation en temps réel** permettant de récupérer les coordonnées GPS d’un appareil mobile, de les envoyer vers un backend PHP/MySQL, puis de les afficher sur une carte Google Maps sous forme de marqueurs.

Ce laboratoire met en place une chaîne complète :

```text
Android GPS → Volley HTTP POST → PHP API → MySQL → JSON API → Google Maps Markers
```

---

## 🎯 Objectif du laboratoire

Le but de ce laboratoire est de :

- Comprendre l’utilisation du **GPS Android** avec Java
- Gérer les **permissions runtime** de localisation
- Envoyer des données depuis Android vers un serveur avec **Volley**
- Créer une API PHP basée sur une architecture organisée
- Stocker les positions GPS dans une base **MySQL**
- Récupérer les positions sous forme de **JSON**
- Afficher les coordonnées enregistrées sur **Google Maps**
- Produire une application complète, fonctionnelle et visuellement moderne

---

## 🧭 Description de l’application

**GeoPulse Live** est une application mobile qui permet de suivre et visualiser des positions GPS.

L’application contient deux écrans principaux :

### 📍 Écran principal

L’écran principal affiche :

- La latitude actuelle
- La longitude actuelle
- La précision GPS
- L’état de synchronisation avec le serveur
- Un bouton permettant d’ouvrir la carte

### 🗺️ Écran Google Maps

L’écran carte permet de :

- Récupérer les positions stockées dans MySQL
- Afficher chaque position sous forme de marqueur
- Visualiser la date de capture
- Identifier l’appareil associé à la position
- Centrer automatiquement la caméra sur les points enregistrés

---

## ✨ Fonctionnalités

- Récupération de la position GPS en temps réel
- Demande automatique de permission localisation
- Envoi des coordonnées vers une API PHP
- Insertion des données dans une table MySQL
- Récupération des positions en JSON
- Affichage dynamique des marqueurs sur Google Maps
- Interface Android personnalisée avec :
  - Fond en dégradé
  - Cartes arrondies
  - Bouton moderne
  - Couleurs harmonieuses
  - Présentation claire des coordonnées

---

## 🧰 Technologies utilisées

| Catégorie | Technologies |
|---|---|
| Mobile | Android Studio, Java, XML |
| Réseau | Volley |
| Localisation | Android LocationManager |
| Carte | Google Maps SDK |
| Backend | PHP |
| Base de données | MySQL |
| Serveur local | XAMPP |
| Format d’échange | JSON |
| API minimum | Android 7.0 / API 24 |

---

## ▶️ Démonstration

Une démonstration vidéo complète est disponible dans le dossier **Demo** du repository.

```text
Demo/
└── demo_geopulse_live.mp4
```

La vidéo montre :

- Le test de l’API PHP
- L’insertion dans MySQL
- La récupération JSON des positions
- Le lancement de l’application Android
- L’affichage des coordonnées GPS
- L’ouverture de Google Maps
- L’apparition des marqueurs sur la carte

---

## 🗄️ Base de données

### Base utilisée

```sql
CREATE DATABASE localisation;
```

### Table utilisée

La table utilisée dans ce laboratoire est :

```sql
CREATE TABLE position (
  id int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  latitude double NOT NULL,
  longitude double NOT NULL,
  date_position datetime NOT NULL,
  imei varchar(80) NOT NULL
);
```

### Structure finale

| Champ | Type | Rôle |
|---|---|---|
| id | int | Identifiant unique auto-incrémenté |
| latitude | double | Latitude GPS |
| longitude | double | Longitude GPS |
| date_position | datetime | Date et heure de capture |
| imei | varchar(80) | Identifiant de l’appareil |

---

## 🧩 Architecture globale

```text
GeoPulseLive/
│
├── Android App
│   ├── MainActivity.java
│   ├── LiveMapActivity.java
│   ├── activity_main.xml
│   └── activity_live_map.xml
│
└── PHP Backend
    ├── model/
    │   └── GeoPoint.php
    ├── database/
    │   └── DbConnector.php
    ├── contract/
    │   └── CrudGateway.php
    ├── service/
    │   └── GeoPointService.php
    ├── createPosition.php
    └── showPositions.php
```

---

## 🖥️ Backend PHP

Le backend est placé dans le dossier :

```text
C:\xampp\htdocs\localisation
```

### Arborescence backend

```text
localisation/
├── model/
│   └── GeoPoint.php
├── database/
│   └── DbConnector.php
├── contract/
│   └── CrudGateway.php
├── service/
│   └── GeoPointService.php
├── createPosition.php
└── showPositions.php
```

---

## 📌 Rôle des fichiers PHP

### `model/GeoPoint.php`

Représente une position GPS comme un objet.

Il contient :

- id
- latitude
- longitude
- date de capture
- identifiant appareil

---

### `database/DbConnector.php`

Gère la connexion à la base MySQL avec PDO.

Ce fichier permet :

- D’ouvrir une connexion vers MySQL
- D’activer les erreurs PDO
- De centraliser la configuration de la base de données

---

### `contract/CrudGateway.php`

Définit une interface commune pour les opérations principales.

Elle contient :

- `insert()`
- `findAll()`

---

### `service/GeoPointService.php`

Contient la logique d’accès aux données.

Il permet de :

- Insérer une nouvelle position
- Lire toutes les positions
- Adapter le champ PHP `date` à la colonne MySQL `date_position`

---

### `createPosition.php`

Endpoint utilisé pour enregistrer une position envoyée depuis Android.

Il reçoit :

```text
latitude
longitude
date
imei
```

Puis il insère les données dans MySQL.

---

### `showPositions.php`

Endpoint utilisé par l’activité Google Maps.

Il renvoie les positions au format JSON afin de les afficher sur la carte.

---

## 🌐 API REST locale

### 1. Insertion d’une position

```http
POST http://192.168.43.182/localisation/createPosition.php
```

Header :

```http
Content-Type: application/x-www-form-urlencoded
```

Body :

```text
latitude=31.634679&longitude=-8.019153&date=2026-05-28+17:30:00&imei=test-device-001
```

Réponse attendue :

```json
{
  "success": true,
  "message": "Position enregistrée avec succès",
  "client_ip": "192.168.43.182"
}
```

---

### 2. Récupération des positions

```http
POST http://192.168.43.182/localisation/showPositions.php
```

Réponse attendue :

```json
{
  "success": true,
  "positions": [
    {
      "id": "1",
      "latitude": "31.634679",
      "longitude": "-8.019153",
      "date": "2026-05-28 17:30:00",
      "imei": "test-device-001"
    }
  ]
}
```

---

## 📱 Partie Android

### `AndroidManifest.xml`

Le manifeste contient les permissions nécessaires :

```xml
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.READ_PHONE_STATE" />
```

L’application autorise aussi les requêtes HTTP locales :

```xml
android:usesCleartextTraffic="true"
```

La clé Google Maps est déclarée avec :

```xml
<meta-data
    android:name="com.google.android.geo.API_KEY"
    android:value="${MAPS_API_KEY}" />
```

---

## 🔑 Clé Google Maps

La clé API Google Maps est placée dans :

```text
local.properties
```

Sous la forme :

```properties
MAPS_API_KEY=YOUR_GOOGLE_MAPS_API_KEY
```

Le fichier `local.properties` ne doit pas être poussé sur GitHub.

---

## 🎨 Interface graphique

L’application utilise une interface personnalisée afin d’éviter un rendu basique.

### Éléments visuels utilisés

- Fond en dégradé bleu/violet
- Cartes arrondies effet moderne
- Boutons colorés
- Textes hiérarchisés
- Section GPS claire et lisible
- Design adapté à une démonstration académique

### Fichiers de design

```text
res/drawable/
├── bg_screen_orbit.xml
├── bg_glass_card.xml
├── bg_metric_card.xml
└── bg_location_button.xml
```

---

## 🧪 Tests réalisés

### Test 1 — Création de la base et vérification de la table

Commande utilisée :

```sql
SELECT * FROM position;
```

Résultat attendu :

```text
La table existe et peut recevoir des positions GPS.
```

---

### Test 2 — Insertion via API PHP

Endpoint testé :

```text
createPosition.php
```

Résultat obtenu :

```json
{
  "success": true,
  "message": "Position enregistrée avec succès"
}
```

Ce test valide que le backend reçoit bien les paramètres envoyés en POST.

---

### Test 3 — Vérification dans MySQL

Commande utilisée :

```sql
SELECT * FROM position;
```

Résultat attendu :

```text
Une nouvelle ligne apparaît dans la table position.
```

---

### Test 4 — Récupération JSON

Endpoint testé :

```text
showPositions.php
```

Résultat attendu :

```json
{
  "success": true,
  "positions": [...]
}
```

Ce test valide que l’API peut renvoyer les positions stockées.

---

### Test 5 — Permission GPS Android

Au lancement de l’application, Android demande la permission de localisation.

Résultat attendu :

```text
La permission est demandée et l’application démarre le suivi GPS après acceptation.
```

---

### Test 6 — Envoi depuis l’application Android

Lorsque la position est détectée, l’application envoie automatiquement :

- latitude
- longitude
- date
- identifiant appareil

vers `createPosition.php`.

Résultat attendu :

```text
Une nouvelle position apparaît dans MySQL.
```

---

### Test 7 — Affichage Google Maps

En cliquant sur le bouton :

```text
Afficher la carte
```

L’application ouvre Google Maps et affiche les positions enregistrées sous forme de marqueurs.

Résultat attendu :

```text
Les marqueurs apparaissent sur la carte.
```

---

## 📸 Aperçu de l’application

Les captures d’écran du laboratoire sont disponibles dans le dossier :

```text
screenshots/
```

Captures recommandées pour le rapport :

- Structure du projet PHP
- Table MySQL `position`
- Test réussi de `createPosition.php`
- Réponse JSON de `showPositions.php`
- Interface principale Android
- Permission localisation
- Carte Google Maps avec marqueurs
- Détail d’un marqueur

---

## 🧭 Scénario de démonstration

Le scénario suivi dans la vidéo de démonstration est :

```text
1. Lancement de XAMPP
2. Vérification de la base MySQL
3. Test de createPosition.php
4. Vérification de l’insertion dans phpMyAdmin
5. Test de showPositions.php
6. Lancement de l’application Android
7. Autorisation de la localisation
8. Affichage latitude / longitude / précision
9. Envoi automatique vers le serveur
10. Ouverture de Google Maps
11. Affichage des marqueurs enregistrés
```

---

## ⚙️ Configuration réseau

Pour un téléphone physique, l’application utilise l’adresse IP du PC serveur :

```text
http://192.168.43.182/localisation/
```

Le téléphone et le PC doivent être connectés au même réseau Wi-Fi ou hotspot.

Pour un émulateur Android, l’adresse peut être remplacée par :

```text
http://10.0.2.2/localisation/
```

---

## 🧠 Points techniques importants

- `localhost` ne fonctionne pas depuis un téléphone physique
- L’adresse IP utilisée doit être celle du PC qui exécute XAMPP
- Apache et MySQL doivent être démarrés
- Le téléphone et le PC doivent être sur le même réseau
- La permission GPS doit être acceptée
- La clé Google Maps doit être valide
- Le trafic HTTP local nécessite `usesCleartextTraffic="true"`

---

## 🛡️ Améliorations apportées

Cette version du laboratoire contient plusieurs améliorations par rapport à une version basique :

- Architecture PHP personnalisée
- Noms de classes plus clairs et différents
- Utilisation de PDO
- Requêtes préparées
- Validation des paramètres côté serveur
- Réponses JSON structurées
- Support de `ANDROID_ID` pour identifier l’appareil
- Interface Android personnalisée
- Marqueurs Google Maps avec détails
- Organisation professionnelle du projet

---

## 📂 Structure finale du repository

```text
GeoPulseLive/
├── app/
│   └── src/
│       └── main/
│           ├── java/com/malak/geopulselive/
│           │   ├── MainActivity.java
│           │   └── LiveMapActivity.java
│           ├── res/
│           │   ├── layout/
│           │   ├── drawable/
│           │   └── values/
│           └── AndroidManifest.xml
│
├── backend/
│   └── localisation/
│       ├── model/
│       ├── database/
│       ├── contract/
│       ├── service/
│       ├── createPosition.php
│       └── showPositions.php
│
├── Demo/
│   └── demo_geopulse_live.mp4
│
├── screenshots/
│   └── captures_du_lab
│
└── README.md
```

---

## ✅ Résultat final

À la fin du laboratoire, l’application permet de :

- Capturer une position GPS depuis Android
- Envoyer les coordonnées vers un serveur PHP
- Stocker les données dans MySQL
- Récupérer l’historique des positions
- Afficher les positions sur Google Maps

---

## 🏁 Conclusion

Ce laboratoire met en œuvre une application mobile complète combinant :

- Programmation Android en Java
- Localisation GPS
- Communication réseau avec Volley
- Backend PHP
- Base de données MySQL
- Consommation JSON
- Google Maps SDK

Il permet de comprendre comment une application mobile peut collecter des données de localisation, les stocker sur un serveur local et les afficher visuellement sur une carte interactive.

**GeoPulse Live** constitue ainsi une base solide pour développer des systèmes plus avancés de suivi GPS, de géolocalisation temps réel ou de supervision mobile.
