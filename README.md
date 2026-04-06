# docker-laba-1

Spring Boot API + PostgreSQL в Docker с CI/CD на GitHub Actions.

## Требования

- Docker и Docker Compose
- Java 21 (для локального Maven запуска)
- Maven 3.9+ (или `./mvnw` в модуле)

## Локальный запуск

1. Создайте `.env` в корне проекта:

```env
POSTGRES_USER=postgres
POSTGRES_PASSWORD=your_strong_password
```

2. Поднимите сервисы:

```bash
docker compose up --build
```

3. API будет доступен на `http://localhost:8080`.
   База данных доступна только внутри docker-сети (без port-forwarding).

## Структура

- `docker-compose.yaml` — orchestration сервисов
- `db/Dockerfile` + `db/init.sql` — контейнер PostgreSQL
- `users/users/Dockerfile` — multi-stage сборка Spring Boot
- `users/users/checkstyle.xml` — конфигурация линтера
- `users/users/pom.xml` — тесты, линтер и coverage (JaCoCo)
- `.github/workflows/ci.yml` — CI/CD pipeline

## CI/CD (GitHub Actions)

Pipeline запускается для pull request и push.

Stages:

1. `build` — проверка сборки приложения
2. `lint` — запуск Checkstyle
3. `test` — тесты + отчёт JaCoCo + проверка порога coverage (>= 50%)
4. `docker_build` — сборка Docker-образа с тегом `:<branch>-<commit_sha>`
5. `docker_push` — push образа в Docker Hub (только для push в `main`)

### Секреты GitHub (Settings -> Secrets and variables -> Actions)

- `DOCKERHUB_USERNAME` — логин Docker Hub
- `DOCKERHUB_TOKEN` — токен Docker Hub
- `DOCKERHUB_REPOSITORY` — репозиторий образа, например `username/users-app`

Секреты не хардкодятся в репозитории и берутся только из переменных среды.

## Локальная проверка quality gates

Из корня проекта:

```bash
mvn -f users/users/pom.xml clean verify
```

Команда выполнит:
- сборку,
- lint (checkstyle),
- тесты,
- генерацию coverage,
- падение при coverage ниже 50%.
