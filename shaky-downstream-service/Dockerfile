FROM eclipse-temurin:19-jre

ADD target/shaky-downstream-service*.jar /app.jar

ENTRYPOINT java $JAVA_OPTS -jar app.jar