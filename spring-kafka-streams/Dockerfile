FROM eclipse-temurin:19-jre

ADD target/spring-kafka-streams*.jar /app.jar

ENTRYPOINT java $JAVA_OPTS -jar app.jar