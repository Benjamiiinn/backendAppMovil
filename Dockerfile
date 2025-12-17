# --- Etapa 1: Construcción (Build) ---
# Usamos una imagen con Maven y Java 21 oficial
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copiamos todo tu código al contenedor
COPY . .

# Ejecutamos el comando para crear el .jar (saltando los tests para ir rápido)
RUN mvn clean package -DskipTests

# --- Etapa 2: Ejecución (Run) ---
# Usamos una imagen ligera solo con Java 21 (sin Maven, para ahorrar espacio)
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copiamos el .jar generado en la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Exponemos el puerto (informativo)
EXPOSE 8080

# Comando para arrancar la app.
# Usamos -Dserver.port=$PORT para que tome el puerto que Render le asigne.
ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT:-8080}"]