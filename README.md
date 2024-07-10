# Bagueton API

Ceci est le backend de l'application Bagueton, une application Android d'outils de boulangerie.

## À propos

Bagueton API est la seconde partie de mon projet d'étude "Bagueton" servant de projet d'étude pour le diplôme Développeur Web & Web Mobile Bac +2.

## Fonctionnalités

- **Création de recettes** : Les utilisateurs peuvent créer des recettes en fournissant un titre, une option d'image, une liste d'ingrédients, et des étapes de préparation.
- **Liste de recettes** : Les utilisateurs peuvent consulter toutes les recettes disponibles dans l'application.
- **Mise à jour de recette** : Les utilisateurs peuvent mettre à jour leurs recette, modifier les étapes, les ingrédients..
- **Supression de leurs recette** : Les utilisateurs peuvent supprimer une recette s'il le veulent.
  
## Technologies Utilisées

- **Backend** : Kotlin avec Spring Boot pour la logique serveur et l'accès aux données.
- **Base de données** : Utilisation de JPA (Java Persistence API) pour la gestion des entités et l'interaction avec la base de données MySQL.
- **Frontend** : Application Android [disponible ici](https://github.com/CedricSanchezGithub/Bagueton_v1).

Docker compose a été utilisé pour le développement.
Docker & Kubernetes ont été utilisés pour le déploiement CD/CI: l'API était disponible sur internet grâce à Kubernetes sur un Raspberry pi 4
L'image Spring boot du serveur était mise à jour sur mon Raspberry grâce à un github workflow
![cd/ci](https://github.com/CedricSanchezGithub/Bagueton_API/assets/129597649/f9fbd595-e86f-4d11-a76d-3535f8418955)
