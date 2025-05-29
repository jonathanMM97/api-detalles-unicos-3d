# Usa una imagen con Java ya instalado
FROM eclipse-temurin:17-jdk-alpine

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia y construye la app usando Maven wrapper
COPY . .

RUN chmod +x mvnw && ./mvnw clean package -DskipTests

# Expón el puerto que Spring Boot usará (ajústalo si usas otro)
EXPOSE 8080

# Ejecuta la aplicación Spring Boot
CMD ["java", "-jar", "target/tu-app.jar"]