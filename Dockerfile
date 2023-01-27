FROM openjdk:8
ADD feedbot-1.0-jar-with-dependencies.jar
ENTRYPOINT ["java", "-jar","feedbot-1.0-jar-with-dependencies.jar"]