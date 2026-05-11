# SafeLogin Back - API Spring Boot

Backend Java du projet SafeLogin.

# SafeLogin - Fullstack Auth App

SafeLogin est une application d'authentification fullstack (frontend + backend) construite pour démontrer une architecture propre, la sécurité des mots de passe et la protection des routes avec JWT.


API REST d'authentification avec validation, hashage BCrypt, JWT, rôles (`USER`/`ADMIN`) et protection des routes via Spring Security.

## Stack

- Java 17
- Spring Boot 3
- Spring Security
- Spring Data JPA / Hibernate
- JWT (`io.jsonwebtoken`)
- MySQL
- Maven Wrapper (`./mvnw`)

## Architecture

```text
src/main/java/com/safelogin/
  controller/
  service/
  repository/
  entity/
  dto/
  config/
```

## Endpoints

### Public
- `GET /test`
- `POST /register`
- `POST /login`

### Authentifiés
- `GET /profile`
- `GET /dashboard`
- `PUT /change-password`

### Admin
- `GET /users`
- `DELETE /users/{id}`

## Sécurité

- Mot de passe hashé avec BCrypt
- Password jamais renvoyé au front
- JWT généré au login
- Filtre JWT (`OncePerRequestFilter`) pour lire `Authorization: Bearer <token>`
- Gestion `401` / `403` JSON côté sécurité

## Validation et erreurs

- Validation DTO avec `@NotBlank`, `@Size`
- Gestion d'erreurs globale via `@RestControllerAdvice`
- Réponse standard de type:

```json
{
  "success": true,
  "message": "...",
  "token": "..."
}
```

## Configuration locale

Fichier: `src/main/resources/application.properties`

Configuration actuelle (MAMP):

```properties
spring.datasource.url=jdbc:mysql://127.0.0.1:8889/safelogin
spring.datasource.username=root
spring.datasource.password=root
```

## Lancer le backend

```bash
cd /Users/omnyazer/eclipse-workspace/safelogin-back
./mvnw spring-boot:run
```

API disponible sur `http://localhost:8080`.

## Vérification rapide

### Register

```bash
curl -i -X POST http://localhost:8080/register \
  -H "Content-Type: application/json" \
  -d '{"username":"demo_user","password":"123456"}'
```

### Login

```bash
curl -i -X POST http://localhost:8080/login \
  -H "Content-Type: application/json" \
  -d '{"username":"demo_user","password":"123456"}'
```

### Profile (avec token)

```bash
curl -i http://localhost:8080/profile \
  -H "Authorization: Bearer TON_TOKEN"
```

## Repo frontend

Le frontend React/Vite est dans le repo `safelogin-front`.
