FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# mvnw ko executable permission do
COPY . .
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

EXPOSE 8089

CMD ["java", "-jar", "target/ecommerce-0.0.1-SNAPSHOT.jar"]