# Usar uma imagem base do OpenJDK 17
FROM openjdk:17-jdk-slim

# Definir diretório de trabalho
WORKDIR /app

# Copiar o arquivo WAR para o contêiner
COPY target/park_api-0.0.1-SNAPSHOT.war /app/park_api-0.0.1-SNAPSHOT.war

# Expor a porta que o aplicativo vai rodar
EXPOSE 8080

# Comando para rodar a aplicação
CMD ["java", "-jar", "/app/park_api-0.0.1-SNAPSHOT.war"]
