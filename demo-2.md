# Demo 2 (Kosta)

## Start infrastructure + applications

Make sure to build the Docker images for the applications first

```bash
./buildDockerImages.sh
```

Run all the docker containers:

```bash
docker-compose -f docker-compose.yml -f docker-compose-applications.yml up -d
```

## Access Swagger UI (producer)

* http://localhost:8080/swagger-ui/index.html

* Send stock quote message payload

```json
{
  "symbol": "INGA",
  "exchange": "AMS",
  "tradeValue": "11.53",
  "currency": "EUR",
  "description": "ING Groep NV"
}
```

* Send stock quote message payload that will cause error

```json
{
  "symbol": "KABOOM",
  "exchange": "AMS",
  "tradeValue": "666.6",
  "currency": "EUR",
  "description": "Trigger an exception"
}
```

## Important links to components to show in the demo 

* [Jaeger](http://localhost:16686/search)
* [Grafana Tempo](http://localhost:3000/explore)
* [Grafana Dashboards](http://localhost:3000/dashboards)
  * [Grafana Loki Dashboard](http://localhost:3000/d/loki/loki-logs-dashboard?orgId=1)

## Shutdown 

```bash
docker-compose -f docker-compose.yml -f docker-compose-applications.yml down -v
```