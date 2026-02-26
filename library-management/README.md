# Library Management System - Spring Boot Backend

## 📌 Overview
A complete REST API for managing book categories and books, built with **Java Spring Boot 3.2**, **Spring Data JPA**, and **H2 in-memory database** (switchable to MySQL).

---

## 🛠️ Tech Stack
- **Java 17**
- **Spring Boot 3.2**
- **Spring Data JPA**
- **Spring Validation**
- **H2 Database** (in-memory, no setup needed)
- **Lombok**
- **Maven**

---

## 🚀 How to Run

### Prerequisites
- Java 17+
- Maven 3.8+

### Steps
```bash
cd library-management
mvn clean install
mvn spring-boot:run
```

The server starts at: `http://localhost:8080`

H2 Console: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:librarydb`
- Username: `sa`
- Password: *(leave blank)*

---

## 🔄 Switch to MySQL
In `src/main/resources/application.properties`:
1. Comment out the H2 config block
2. Uncomment the MySQL config block
3. Update your MySQL username/password
4. Add MySQL dependency in pom.xml (already present, just uncomment)

---

## 📋 API Endpoints

### Category APIs
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/categories` | Get all categories |
| GET | `/categories/{id}` | Get category by ID |
| POST | `/categories` | Create new category |
| PUT | `/categories/{id}` | Update category |
| DELETE | `/categories/{id}` | Delete category |

### Book APIs
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/books` | Get all books |
| GET | `/books?name=xyz` | Search books by name |
| GET | `/books?categoryId=1` | Filter books by category |
| GET | `/books?name=xyz&categoryId=1` | Search + filter |
| GET | `/books/{id}` | Get book by ID |
| GET | `/books/category/{categoryId}` | Get all books in a category |
| POST | `/books` | Create new book |
| PUT | `/books/{id}` | Update book |
| DELETE | `/books/{id}` | Delete book |

---

## 📝 Request/Response Examples

### Create Category
**POST** `/categories`
```json
{
  "name": "Science Fiction",
  "description": "Sci-fi books"
}
```

### Create Book
**POST** `/books`
```json
{
  "bookName": "Dune",
  "author": "Frank Herbert",
  "isbn": "978-0-441-01359-7",
  "publishedDate": "1965-08-01",
  "categoryId": 1
}
```

### Sample Success Response
```json
{
  "success": true,
  "message": "Book created successfully",
  "data": {
    "id": 1,
    "bookName": "Dune",
    "author": "Frank Herbert",
    "isbn": "978-0-441-01359-7",
    "publishedDate": "1965-08-01",
    "categoryId": 1,
    "categoryName": "Science Fiction"
  }
}
```

### Sample Error Response (Validation)
```json
{
  "success": false,
  "message": "Validation failed",
  "data": {
    "bookName": "Book name is required",
    "isbn": "ISBN is required"
  }
}
```

---

## ✅ Validations
- **Category Name**: Required, must be unique
- **Book Name**: Required
- **Author**: Required
- **ISBN**: Required, must be unique
- **Published Date**: Required (format: YYYY-MM-DD)
- **Category ID**: Required, must reference an existing category

---

## 📁 Project Structure
```
src/main/java/com/library/
├── LibraryManagementApplication.java
├── config/
│   ├── CorsConfig.java
│   └── DataInitializer.java
├── controller/
│   ├── CategoryController.java
│   └── BookController.java
├── service/
│   ├── CategoryService.java
│   └── BookService.java
├── repository/
│   ├── CategoryRepository.java
│   └── BookRepository.java
├── entity/
│   ├── Category.java
│   └── Book.java
├── dto/
│   ├── CategoryDTO.java
│   ├── BookDTO.java
│   └── ApiResponse.java
└── exception/
    ├── ResourceNotFoundException.java
    ├── DuplicateResourceException.java
    └── GlobalExceptionHandler.java
```
