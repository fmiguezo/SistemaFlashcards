
# 📚 Sistema de Flashcards

Este repositorio contiene un sistema de gestión de flashcards desarrollado en Java, utilizando principios de **arquitectura hexagonal (Ports & Adapters)** para lograr un alto desacoplamiento entre la lógica de negocio y la infraestructura.

---

## 🧠 ¿Qué es una flashcard?

Una *flashcard* es una tarjeta que contiene una pregunta (front) y una respuesta (back). Las tarjetas se agrupan en *decks* (conjuntos de estudio).

---

## 📦 Características principales

- Crear decks con múltiples flashcards.
- Persistencia en MongoDB o archivos JSON.
- Arquitectura desacoplada, flexible y fácilmente testeable.
- Uso de DTOs y mappers para entrada/salida.
- Soporte para repetición espaciada (en proceso).

---

## 🧱 Estructura del proyecto (Hexagonal)

[Contenido con árbol del proyecto y subcarpetas — copiado tal cual del mensaje del usuario para mantener fidelidad]

---

## 📂 ¿Qué hay en cada carpeta?

El proyecto sigue una arquitectura hexagonal. Aquí se explica la función de cada carpeta y qué debe contener:

### `domain/`
Contiene la lógica de negocio central y no depende de frameworks externos.

- `model/`: modelos puros como `Deck` y `Flashcard`.
- `port/in/`: interfaces de entrada (casos de uso) como `CreateDeckUseCase`.
- `port/out/`: interfaces de salida como `SaveDeckPort`.

### `application/`
Implementa los casos de uso orquestando el dominio.

- `service/`: lógica que implementa los puertos de entrada.
- `mappers/`: conversores entre modelos del dominio y entidades/DTOs.

### `adapters/`
Contiene los adaptadores concretos que conectan el mundo exterior con el dominio.

- `in/rest/`: controladores HTTP REST.
- `out/postgres/`: persistencia PostgreSQL.
- `out/json/`: persistencia en archivos JSON.
- `out/entity/`: entidades JPA (`DeckEntity`, etc.).
- `out/jsonmodel/`: clases que representan estructuras JSON.

### `infrastructure/`
Contiene configuraciones técnicas.

- `config/`: wiring y configuración de beans de Spring.

---

# 🧠 Sistema Flashcards - Módulo de Mappers

## 📌 ¿Qué es un Mapper?

Los mappers son clases responsables de **convertir objetos entre diferentes capas** de la aplicación:

* **Capa de dominio (`domain.model`)**
* **Capa de persistencia (`entity`)**
* **Capa de entrada/salida de datos (`jsonmodel` y `dto`)**

---

## 🧩 Clases de Mapeo

### 1. `FlashcardMapper`
Encargado de transformar objetos `Flashcard` entre sus representaciones:

#### 📃 ENTITY ↔ DOMAIN
```java
//Flashcard toDomain(FlashcardEntity entity)
//FlashcardEntity toEntity(Flashcard domain, DeckEntity deck)
```

#### 📦 JSON ↔ DOMAIN
```java
//Flashcard toDomain(FlashcardJson json)
//FlashcardJson toJson(Flashcard domain)
```

#### 📥 DTO (Request/Response) ↔ DOMAIN
```java
//Flashcard toDomain(CreateFlashcardRequest request)
//FlashcardResponse toResponse(Flashcard domain)
```

---

### 2. `DeckMapper`
Realiza el mapeo completo de los objetos `Deck` incluyendo sus listas de `Flashcard`.

#### 📃 ENTITY ↔ DOMAIN
```java
//Deck toDomain(DeckEntity entity)
//DeckEntity toEntity(Deck domain)
```

#### 📦 JSON ↔ DOMAIN
```java
// toDomain(DeckJson json)
//DeckJson toJson(Deck domain)
```

#### 📥 DTO (Request/Response) ↔ DOMAIN
```java
//Deck toDomain(CreateDeckRequest request)
//DeckResponse toResponse(Deck domain)
```

---

## 📊 Ventajas de esta arquitectura

* **Separación de responsabilidades**.
* **Testabilidad**.
* **Flexibilidad**.

---

## ✅ Ejemplo de flujo completo

1. `CreateDeckRequest` llega al controlador.
2. `DeckMapper.toDomain(request)` → modelo del dominio.
3. `DeckRepository` guarda con `DeckMapper.toEntity(domain)`.
4. Respuesta: `DeckMapper.toResponse(domain)`.

---

## 🚀 Tecnologías utilizadas

- Java 17
- Spring Boot
- PostgreSQL o almacenamiento en archivos
- Maven
- Jackson (JSON)
- UUID y LocalDateTime

---

## 📄 Endpoints (REST)

### Crear un deck
`POST /decks`
```json
{
	"id": "1",
    "front": "¿Cuál es la capital de Francia?",
    "back": "París",
    "createdAt": "2024-03-20T10:00:00",
    "updatedAt": "2024-03-20T10:00:00",
    "estrategiaDeRepeticion": "PROXIMO_REPASO",
    "fechaDeUltimaRevision": "2024-03-20T10:00:00",
    "deckId": "1"
}
```

### Obtener un deck
`GET /decks/{id}`

---

## 🛠 Cómo correr el proyecto

1. Clonar el repo:
```bash
git clone https://github.com/tu-usuario/SistemaFlashcards.git
cd SistemaFlashcards
```

2. Configurar `application.properties`:

**PostgreSQL**:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/flashcards
spring.datasource.username=usuario
spring.datasource.password=clave
spring.jpa.hibernate.ddl-auto=update
```

**JSON**: sin configuración especial para uso local.

3. Ejecutar:
```bash
./mvnw spring-boot:run
```
