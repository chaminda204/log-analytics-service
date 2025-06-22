# 📊 Log Analytics Service

Application for analyzing web server log files. Upload an HTTP log file and get analytics such
as:

- ✅ Number of unique IP addresses
- ✅ Most frequent IP addresses
- ✅ Most visited URLs

The MVP implements only the happy path and does not include validations (e.g., file format or size checks), security
features, virus scanning, or error handling.

### Product backlog

- Error handling
- Validations
- Security

### Tech Improvements

- Add test coverage
- Separate unit and integration tests

---

## 🛠️ Tech Stack

- **Java 21**
- **Spring Boot 3 (WebFlux)**
- **Docker**
- **Spock Framework** for unit testing
- **RestAssured** for REST endpoint testing

---

## 🚀 Running the Application

### Prerequisites

- Java 21+
- Gradle (wrapper included)

## Build and Run using local

#### local dev

```bash
./gradlew clean build
./gradlew bootRun
```

#### running docker container

```bash
./gradlew clean build
docker compose up --build
```

### Invoking the API

``` curl
curl -X POST http://localhost:8080/api/analytics/logs \
-H "Content-Type: multipart/form-data" \
-F "file=@path-to-your-log-file/your-log-file-name.log"
```

### Sample response

```json
{
  "results": {
    "mostFrequentIpAddress": [
      {
        "ipAddress": "168.41.191.40",
        "occurrences": 4
      },
      {
        "ipAddress": "50.112.00.11",
        "occurrences": 3
      },
      {
        "ipAddress": "177.71.128.21",
        "occurrences": 3
      }
    ],
    "noOfUniqueIpAddresses": 11,
    "mostVisitedUrls": [
      {
        "endpoint": "GET /docs/manage-websites/",
        "occurrences": 2
      },
      {
        "endpoint": "GET /intranet-analytics/",
        "occurrences": 1
      },
      {
        "endpoint": "GET /this/page/does/not/exist/",
        "occurrences": 1
      }
    ]
  }
}

```

