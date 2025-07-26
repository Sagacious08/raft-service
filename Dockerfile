FROM openjdk:17
EXPOSE 8080
ADD target/raft-service.jar raft-service.jar
ENTRYPOINT ["java", "-jar", "raft-service.jar"]