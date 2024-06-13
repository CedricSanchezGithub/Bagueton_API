# Utiliser une image de base compatible avec ARM
FROM adoptopenjdk:17-jdk-hotspot-bionic AS build

# Définir le répertoire de travail
WORKDIR /app

# Installer les fichiers de politique JCE illimités
RUN apt-get update && apt-get install -y wget \
    && wget -O /tmp/jce_policy-8.zip https://www.oracle.com/java/technologies/javase-jce8-downloads.html \
    && unzip -j -o /tmp/jce_policy-8.zip *.jar -d /usr/lib/jvm/java-8-openjdk-amd64/jre/lib/security \
    && rm /tmp/jce_policy-8.zip

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
