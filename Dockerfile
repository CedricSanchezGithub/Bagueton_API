# Utiliser une image de base compatible avec ARM
FROM eclipse-temurin:17-jdk-jammy AS build

# Définir le répertoire de travail
WORKDIR /app

# Copier les fichiers de projet dans le répertoire de travail
COPY . .

# Construire le fichier JAR
RUN ./gradlew bootJar

# Utiliser une nouvelle image de base pour exécuter l'application
FROM eclipse-temurin:17-jdk-jammy

# Définir le répertoire de travail
WORKDIR /app

# Copier le fichier JAR généré depuis l'étape de build
COPY --from=build /app/build/libs/*.jar app.jar

# Exécuter l'application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
