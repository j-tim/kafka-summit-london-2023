lsof -ti :8082 | sort -rn | head -1 | xargs kill -9
lsof -ti :8084 | sort -rn | head -1 | xargs kill -9
lsof -ti :8085 | sort -rn | head -1 | xargs kill -9
lsof -ti :8080 | sort -rn | head -1 | xargs kill -9
lsof -ti :7999 | sort -rn | head -1 | xargs kill -9
docker compose down -v && docker-compose -f docker-compose.yml -f docker-compose-applications.yml up -d

