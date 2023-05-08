FROM eclipse-temurin:19-jre

ADD target/plain-kafka-consumer-0.0.1-jar-with-dependencies.jar /app.jar

ENTRYPOINT java $JAVA_OPTS -jar app.jar