FROM openjdk:11.0.4-jdk AS builder
WORKDIR /usr/matchscore
COPY . .
RUN chmod +x gradlew
RUN sh gradlew build

FROM openjdk:11.0.4-jre
WORKDIR /usr/matchscore
COPY --from=builder /usr/matchscore/build/libs/server-0.0.1-SNAPSHOT.jar server.jar
CMD ["java", "-jar", "server.jar"]
