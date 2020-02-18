# matchscore-server

Server app for MatchScore project.

# email configuration

Before starting the app, configure Spring Boot Mail. If you will use GMail account, you just need to set `spring.mail.username` and `spring.mail.password` variables in `application.properties` file. Otherwise, you might need to change other `spring.mail` variables. If you are using GMail account and having some troubles with authentication, read [this article](https://support.google.com/accounts/answer/6010255).

# docker configuration

This project can be run on Docker containers. Before running it make sure you have docker and docker-compose on your machine. To run it type the following command in your terminal:
```
$ docker-compose up --build
```
