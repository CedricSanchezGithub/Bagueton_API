# Utiliser une image de base openjdk pour construire le projet
FROM openjdk:17-jdk-slim AS build

# Définir le répertoire de travail
WORKDIR /app

# Copier les fichiers de projet dans le répertoire de travail
COPY . .

# Construire le fichier JAR
RUN ./gradlew bootJar

# Utiliser une nouvelle image de base openjdk pour exécuter l'application
FROM openjdk:17-jdk-slim

# Définir le répertoire de travail
WORKDIR /app

# Copier le fichier JAR généré depuis l'étape de build
COPY --from=build /app/build/libs/Bagueton_API.jar app.jar

# Exécuter l'application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
