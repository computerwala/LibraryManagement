
```markdown
# Library Management System

A RESTful API built with Spring Boot for managing books and authors in a library system.

## Features

- **Author Management**
  - Create, Read, Update, Delete authors
  - List all authors
- **Book Management**
  - Create, Read, Update, Delete books
  - List all books
  - Associate books with authors
- **Validation**
  - Input validation for all required fields
  - Custom error messages
- **Documentation**
  - Swagger/OpenAPI documentation
  - H2 Database Console (for development)
- **Testing**
  - Unit tests for service layer
  - Integration tests for controller layer

## Technologies

- Java 17
- Spring Boot 3.4.4
- Spring Data JPA
- H2 Database 
- MySQL 
- Lombok
- Springdoc OpenAPI
- Maven

## Requirements

- Java 17+
- Maven 3.9+
- MySQL 8+ (optional)

## Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/library-management.git
   cd library-management
   ```

2. **Configure database**  
   Edit `src/main/resources/application.properties`:
   ```properties
   # For H2 (in-memory)
   spring.datasource.url=jdbc:h2:mem:librarydb
   spring.datasource.driver-class-name=org.h2.Driver
   
   # For MySQL
   # spring.datasource.url=jdbc:mysql://localhost:3306/library_db
   # spring.datasource.username=yourusername
   # spring.datasource.password=yourpassword
   ```

3. **Build and run**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

## API Documentation

Access Swagger UI at:  
`http://localhost:8080/swagger-ui.html`

**Sample Endpoints:**
- `POST /api/authors` - Create new author
- `GET /api/books` - List all books
- `PUT /api/books/{id}` - Update book details
- `DELETE /api/authors/{id}` - Delete author

## Testing

Run all tests:
```bash
mvn test
```

**Test Coverage:**
- Service layer unit tests
- Controller layer integration tests
- Validation tests
- Error handling tests

## Database Access (H2 Console)

1. Visit `http://localhost:8080/h2-console`
2. Use these credentials:
   - JDBC URL: `jdbc:h2:mem:librarydb`
   - Username: `sa`
   - Password: (leave empty)

## Postman Collection

Import the included `LibraryManagement.postman_collection.json` into Postman for:
- Pre-configured API requests
- Sample request bodies
- Error scenario examples

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/Library/LibraryManagement/
│   │       ├── controller/      # API endpoints
│   │       ├── service/         # Business logic
│   │       ├── repository/      # Database operations
│   │       ├── model/           # Entities and DTOs
│   │       ├── exception/       # Custom error handling
│   │       └── config/          # Configuration classes
│   └── resources/               # Properties and SQL files
└── test/                        # Unit and integration tests
```

