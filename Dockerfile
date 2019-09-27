FROM openjdk:11.0.4-jre
COPY build/libs/server-0.0.1-SNAPSHOT.jar /usr/matchscore/server.jar
WORKDIR /usr/matchscore
CMD ["java", "-jar", "server.jar"]
