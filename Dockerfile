FROM  adoptopenjdk:17-jdk-hotspot

MAINTAINER lautaroCenizo

# Copia el archivo JAR construido en la fase de construcción
COPY target/Clinica-Odontologica-0.0.1-SNAPSHOT.jar clinica-odontologica.jar

# Comando para ejecutar la aplicación al iniciar el contenedor
ENTRYPOINT ["java", "-jar", "clinica-odontologica.jar"]
