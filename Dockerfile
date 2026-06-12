# Usa uma imagem oficial do Maven com OpenJDK 17 para build
FROM maven:3.9.6-eclipse-temurin-17-alpine AS build
WORKDIR /app
# Copia o pom e baixa as dependências
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o código fonte e faz o build
COPY src ./src
RUN mvn clean package -DskipTests

# Usa uma imagem JRE 17 para rodar
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# Copia o jar do estágio de build
COPY --from=build /app/target/*.jar app.jar
# Expondo a porta padrão do Spring Boot
EXPOSE 8080
# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
