# Utiliser une image de base compatible ARM
FROM arm32v7/adoptopenjdk:17-jdk-hotspot-bionic AS build

# Définir le répertoire de travail
WORKDIR /app

# Copier les fichiers Gradle et le fichier de configuration
COPY gradle /app/gradle
COPY build.gradle /app/
COPY settings.gradle /app/

# Télécharger les dépendances sans les réinstaller à chaque build
RUN ./gradlew downloadDependencies

# Copier le reste des fichiers du projet
COPY . /app

# Construire le fichier JAR
RUN ./gradlew bootJar

# Utiliser une nouvelle image de base pour exécuter l'application
FROM arm32v7/adoptopenjdk:17-jdk-hotspot-bionic

# Copier le fichier JAR généré depuis l'étape de build
COPY --from=build /app/build/libs/Bagueton_API.jar app.jar

# Exécuter l'application
ENTRYPOINT ["java", "-jar", "/app.jar"]
