# Librarymanagementask

 <h2>Project Summary</h2>
I have developed a complete Library Management System REST API using:
• Java 17 + Spring Boot 3.2
• Spring Data JPA + H2 In-Memory Database (MySQL-ready)
• Spring Validation with proper error handling
• RESTful APIs for Categories and Books (full CRUD)
• Global Exception Handler with meaningful error responses
• CORS configured for Angular frontend integration

  mvn spring-boot:run

All API endpoints are working and tested:
✅ GET/POST/PUT/DELETE /categories
✅ GET/POST/PUT/DELETE /books
✅ Search books by name
✅ Filter books by category
✅ Validation with unique constraints (Category Name, ISBN)
