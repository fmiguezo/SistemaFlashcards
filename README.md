
# 📚 Sistema de Flashcards -  ⚠️ En desarrollo ⚠️

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

# Documentación de Clases

## 1. Capa de Entrada

### `edu.utn.Main`

**Responsabilidad:** Punto de arranque de la aplicación. Inicializa componentes (CLI o Web).

**Métodos:**

```java
public static void main(String[] args);
```

### CLI (`edu.utn.infrastructure.adapters.in.CLI`)

**Responsabilidad:** Interfaz de línea de comandos; muestra menús y procesa opciones.

**Métodos:**

```java
void menuPrincipal();
void ejecutarOpcion(int opcion);
// ...
```

### ConsoleInput

**Responsabilidad:** Lectura y parseo de entradas de usuario por consola.

**Métodos:**

```java
int leerEntero(String prompt);
String leerTexto(String prompt);
// ...
```

### REST Controllers (`edu.utn.infrastructure.adapters.in.rest.controller`)

#### DeckController

**Responsabilidad:** Exponer endpoints CRUD para decks (`/api/decks`).

**Métodos:**

```java
ResponseEntity<List<DeckDTO>> getAll();
ResponseEntity<DeckDTO> getById(UUID id);
ResponseEntity<DeckDTO> create(DeckDTO dto);
ResponseEntity<DeckDTO> modify(UUID id, DeckDTO dto);
ResponseEntity<Void> delete(UUID id);
ResponseEntity<Void> practice(UUID id);
```

#### FlashcardController

**Responsabilidad:** Endpoints CRUD para flashcards (`/api/flashcards`).

**Métodos:**

```java
ResponseEntity<List<FlashcardDTO>> getAllByDeck(UUID deckId);
ResponseEntity<FlashcardDTO> getById(UUID id);
ResponseEntity<FlashcardDTO> create(FlashcardDTO dto);
ResponseEntity<FlashcardDTO> modify(UUID id, FlashcardDTO dto);
ResponseEntity<Void> delete(UUID id);
```

#### WebUserPracticeController

**Responsabilidad:** Implementa `IUserPracticeInputPort` para práctica vía web.

**Métodos:**

```java
void showQuestion(Flashcard flashcard);
String askUserForAnswer(Flashcard flashcard);
void showAnswer(Flashcard flashcard);
```

#### ExceptionHandler

**Responsabilidad:** Manejador global de excepciones (`@RestControllerAdvice`).

**Manejadores:**

```java
//@ExceptionHandler(DeckNoExisteException.class)
//@ExceptionHandler(FlashcardError.class)
// ...
```

## 2. DTOs y Errores

### DTOs (`edu.utn.application.dto`)

#### DeckDTO

**Atributos:**

* `UUID id`
* `String nombre`
* `String descripcion`

**Responsabilidad:** Transportar datos de decks entre capas.

**Métodos:** Constructores, getters y setters.

#### FlashcardDTO

**Atributos:**

* `UUID id`
* `String pregunta`
* `String respuesta`

**Responsabilidad:** Transportar datos de flashcards entre capas.

**Métodos:** Constructores, getters y setters.

### Errores (`edu.utn.application.error`)

* `DeckError`, `FlashcardError`
  **Responsabilidad:** Excepciones de negocio para validar reglas (nulos, longitudes, existencia).
  **Constructores:** Mensajes específicos de validación.

## 3. Mappers

### `DeckMapper` (`edu.utn.application.mappers`)

**Responsabilidad:** Convertir entre `Deck` y `DeckDTO`.

**Métodos:**

```java
Deck toDomain(DeckDTO dto);
DeckDTO toDto(Deck deck);
```

### `FlashcardMapper`

**Responsabilidad:** Convertir entre `Flashcard` y `FlashcardDTO`.

**Métodos:**

```java
Flashcard toDomain(FlashcardDTO dto);
FlashcardDTO toDto(Flashcard card);
```

### Persistencia (`infrastructure.adapters.out.persistence.mapper`)

#### `DeckPersistenceMapper`

**Responsabilidad:** Mapear entre `DeckEntity` (JPA) y `Deck` (dominio).

**Métodos:**

```java
Deck toDomain(DeckEntity entity);
DeckEntity toEntity(Deck deck);
```

#### `FlashcardPersistenceMapper`

**Responsabilidad:** Mapear entre `FlashcardEntity` y `Flashcard`.

**Métodos:**

```java
Flashcard toDomain(FlashcardEntity entity);
FlashcardEntity toEntity(Flashcard card);
```

## 4. Casos de Uso

### Decks (`edu.utn.application.usecase.deck`)

* **CreateDeckUseCase:** Valida y crea un deck.
* **GetDeckUseCase:** Obtiene un deck por ID.
* **ListDecksUseCase:** Lista todos los decks.
* **ModifyDeckUseCase:** Actualiza datos de un deck existente.
* **DeleteDeckUseCase:** Elimina un deck.
* **PracticeDeckUseCase:** Orquesta la sesión de práctica de un deck.

### Flashcards (`edu.utn.application.usecase.flashcard`)

* **AddFlashcardToDeckUseCase:** Agrega una flashcard a un deck.
* **CreateFlashcardUseCase**, **GetFlashcardUseCase**, **ListFlashcardsUseCase**, **ModifyFlashcardUseCase**, **DeleteFlashcardUseCase:** Operaciones CRUD para flashcards.

## 5. Modelo de Dominio

### Entidades (`edu.utn.domain.model`)

#### `Deck`

**Atributos:**

* `UUID id`
* `String nombre`
* `String descripcion`
* `LocalDateTime createdAt, updatedAt`
* `List<Flashcard>`

**Responsabilidad:** Lógica de negocio de deck (agregar flashcards, timestamps).

#### `Flashcard`

**Atributos:**

* `UUID id`
* `String pregunta`
* `String respuesta`
* `LocalDateTime createdAt, updatedAt`

**Responsabilidad:** Representar una flashcard con sus datos y timestamps.

### Estrategia de Repetición (`model.estrategia`)

* **`IEstrategiaRepeticion`**: Interfaz para definir orden de práctica.
* **`EstrategiaRepeticionEstandar`**: Implementación por defecto.

## 6. Servicios de Dominio

### Deck (`edu.utn.domain.service.deck`)

* **`IDeckService`**: Interfaz de operaciones de negocio de decks.
* **`DeckService`**: Implementación; orquesta validaciones y llamado a repositorios.

### Flashcard (`edu.utn.domain.service.flashcard`)

* **`IFlashcardService`**: Interfaz de operaciones de flashcards.
* **`FlashcardService`**: Implementación de lógica de flashcards.

### Validación (`edu.utn.domain.service.validation`)

* **`ValidationService`**
  **Responsabilidad:** Validar campos de entrada de DTOs.

**Métodos:** Validaciones de nulos, longitudes, existencia.

## 7. Puertos

### Inbound (`infrastructure.ports.in`)

* **`IUserPracticeInputPort`**: Interfaz para interacción con el usuario durante práctica.

### Outbound (`infrastructure.ports.out`)

* **`IDeckRepository`, `IFlashcardRepository`**: CRUD de persistencia.

## 8. Adaptadores de Persistencia

### Entities (`infrastructure.adapters.out.persistence.entities`)

* `DeckEntity`, `FlashcardEntity`: Clases JPA anotadas.

### Firebase (`infrastructure.adapters.out.firebase`)

* `FirebaseDeckService`: Persiste decks en Firebase.

### JSON (`infrastructure.adapters.out.json`)

* `RepositorioDeDecksJson`, `RepositorioDeCardsJson`: Archivos JSON como almacenamiento.

### PostgreSQL (`infrastructure.adapters.out.postgres`)

* `RepositorioDeDecksPostgres`, `RepositorioDeCardsPostgres`: Repositorios en PostgreSQL.

### Excepciones (`infrastructure.adapters.out.exception`)

* `ArchivoNoEncontradoException`, `DeckNoExisteException`, `DeckVacioException`.

## 9. Configuración

### `FirebaseConfig`

**Responsabilidad:** Bean de inicialización y configuración de Firebase.

### `RepositoryConfig`

**Responsabilidad:** Inyectar la implementación adecuada de repositorios según el perfil activo (`json`, `postgres`, `firebase`).

