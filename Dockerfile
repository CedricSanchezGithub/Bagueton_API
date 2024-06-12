# Utiliser une image de base compatible avec ARM
FROM adoptopenjdk:17-jdk-hotspot-bionic AS build

# Définir le répertoire de travail
WORKDIR /app

# Copier les fichiers de projet dans le répertoire de travail
COPY . .

# Construire le fichier JAR
RUN ./gradlew bootJar

# Utiliser une nouvelle image de base pour exécuter l'application
FROM adoptopenjdk:17-jdk-hotspot-bionic

# Définir le répertoire de travail
WORKDIR /app

# Copier le fichier JAR généré depuis l'étape de build
COPY --from=build /app/build/libs/*.jar app.jar

# Exécuter l'application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
