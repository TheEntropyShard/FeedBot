FROM openjdk:8
ADD target/feedbot-1.0-jar-with-dependencies.jar feedbot-1.0-jar-with-dependencies.jar
ENTRYPOINT ["java", "-jar","feedbot-1.0-jar-with-dependencies.jar"]