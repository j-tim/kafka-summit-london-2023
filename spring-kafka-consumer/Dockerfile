FROM eclipse-temurin:19-jre

ADD target/spring-kafka-consumer*.jar /app.jar

ENTRYPOINT java $JAVA_OPTS -jar app.jar