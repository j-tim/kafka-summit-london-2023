./mvnw clean install
docker-compose -f docker-compose.yml -f docker-compose-applications.yml build --no-cache