FROM openjdk:17-jdk

MAINTAINER lautaroCenizo

# Copia el archivo JAR construido en la fase de construcción
COPY target/Clinica-Odontologica-0.0.1-SNAPSHOT.jar clinica-odontologica.jar

EXPOSE 8080

# Comando para ejecutar la aplicación al iniciar el contenedor
ENTRYPOINT ["java", "-jar", "clinica-odontologica.jar"]
