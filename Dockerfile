
#Build the application with Maven
FROM openjdk:11-jdk-slim as build-stage
WORKDIR /app

RUN apt-get update && apt-get install -y maven

COPY mvnw .
COPY .mvn .mvn


#Copy the Maven project files and download dependencies
COPY pom.xml .
RUN chmod +x .mvn
RUN mvn dependency:go-offline
RUN mvn package

#Copy the application source code
COPY src src

#Build the application
RUN mvn package -DskipTests

#Create final Docker Image
FROM openjdk:11-jre-slim as runtime

WORKDIR /app

#Copy the application JAR from the builder stage
COPY --from=build-stage /app/target/student-rest-api-0.0.1-SNAPSHOT.jar student-rest-api.jar

#Expose port
ENTRYPOINT ["java", "-jar", "student-rest-api.jar"]
EXPOSE 8080



