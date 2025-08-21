# Joke & Quote Microservices

A Java microservices project built with Micronaut framework for managing jokes and quotes with user interactions, powered by MongoDB.

## Architecture

This is a multi-module Maven project with microservices architecture:

```
src/
├── pom.xml (parent)
├── common/ (shared models, DTOs, exceptions)
├── quote-service/ (port 9091)
└── joke-service/ (port 9092)
```

## Technologies

- **Java 21** with GraalVM
- **Micronaut 4.3.1** (lightweight microservices framework)
- **Maven** (multi-module build)
- **MongoDB** (NoSQL database with reactive drivers)
- **Micronaut Data MongoDB** (auto-implemented repositories)
- **Jackson** (JSON serialization)
- **Bean Validation** (request validation)

## Database Setup

### Local Development (MongoDB)
```bash
# Start MongoDB locally (Docker recommended)
docker run -d -p 27017:27017 --name mongodb mongo:latest

# Or install MongoDB locally and ensure it's running on port 27017
```

### Configuration
- **Local**: `application-dev.properties` - MongoDB at `localhost:27017`
- **Production**: `application-prod.properties` - AWS DynamoDB (future implementation)

## Services

### Quote Service (Port 9091)
Manages inspirational quotes with features like:
- CRUD operations for quotes
- Categorization by author/category
- Like/unlike functionality
- Random and top-rated quotes
- Pagination support

### Joke Service (Port 9092)
Manages jokes with approval workflow:
- CRUD operations for jokes
- User management
- Approval system for new jokes
- Like/unlike functionality
- Category filtering
- Random and top-rated jokes

## API Endpoints

### Quote Service (http://localhost:9091/api)

#### Quotes
- `GET /quotes` - Get paginated quotes
   - Query params: `page=0`, `size=10`
- `GET /quotes/{id}` - Get quote by ID (MongoDB ObjectId)
- `GET /quotes/category/{category}` - Get quotes by category
- `GET /quotes/author/{author}` - Get quotes by author
- `GET /quotes/user/{userId}` - Get quotes by user (MongoDB ObjectId)
- `GET /quotes/random?limit=5` - Get random quotes
- `GET /quotes/top?limit=10` - Get top-rated quotes
- `POST /quotes` - Create new quote
- `PUT /quotes/{id}` - Update quote
- `DELETE /quotes/{id}` - Delete quote
- `POST /quotes/{id}/like` - Like quote
- `POST /quotes/{id}/unlike` - Unlike quote

### Joke Service (http://localhost:9092/api)

#### Jokes
- `GET /jokes` - Get paginated jokes (approved by default)
   - Query params: `page=0`, `size=10`, `approvedOnly=true`
- `GET /jokes?approvedOnly=false` - Get all jokes including pending
- `GET /jokes/{id}` - Get joke by ID (MongoDB ObjectId)
- `GET /jokes/category/{category}` - Get approved jokes by category
- `GET /jokes/user/{userId}` - Get jokes by user (MongoDB ObjectId)
- `GET /jokes/pending` - Get pending jokes for approval
- `GET /jokes/random?limit=5` - Get random approved jokes
- `GET /jokes/top?limit=10` - Get top-rated approved jokes
- `POST /jokes` - Create new joke (pending approval)
- `PUT /jokes/{id}` - Update joke
- `DELETE /jokes/{id}` - Delete joke
- `POST /jokes/{id}/like` - Like joke
- `POST /jokes/{id}/unlike` - Unlike joke
- `POST /jokes/{id}/approve` - Approve pending joke
- `POST /jokes/{id}/reject` - Reject pending joke

#### Users
- `GET /users` - Get all users
- `GET /users/{id}` - Get user by ID (MongoDB ObjectId)
- `GET /users/username/{username}` - Get user by username
- `GET /users/email/{email}` - Get user by email
- `POST /users` - Create new user
- `PUT /users/{id}` - Update user
- `DELETE /users/{id}` - Delete user

## Sample Requests

### Create a User (Required First)
```bash
curl -X POST http://localhost:9092/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "username": "developer",
    "email": "dev@example.com",
    "displayName": "John Developer"
  }'
```

Response:
```json
{
  "success": true,
  "message": "User created successfully",
  "data": {
    "id": "507f1f77bcf86cd799439011",
    "username": "developer",
    "email": "dev@example.com",
    "displayName": "John Developer",
    "createdAt": "2024-08-16T10:30:00",
    "updatedAt": "2024-08-16T10:30:00"
  },
  "timestamp": "2024-08-16T10:30:00"
}
```

### Create a Quote
```bash
curl -X POST http://localhost:9091/api/quotes \
  -H "Content-Type: application/json" \
  -d '{
    "text": "The only way to do great work is to love what you do.",
    "author": "Steve Jobs",
    "category": "motivation",
    "userId": "507f1f77bcf86cd799439011"
  }'
```

### Create a Joke
```bash
curl -X POST http://localhost:9092/api/jokes \
  -H "Content-Type: application/json" \
  -d '{
    "setup": "Why did the developer go broke?",
    "punchline": "Because he used up all his cache!",
    "category": "programming",
    "userId": "507f1f77bcf86cd799439011"
  }'
```

### Approve a Pending Joke
```bash
curl -X POST http://localhost:9092/api/jokes/507f1f77bcf86cd799439012/approve \
  -H "Content-Type: application/json"
```

### Get Random Jokes
```bash
curl "http://localhost:9092/api/jokes/random?limit=3"
```

## MongoDB Object IDs

- All entity IDs are MongoDB ObjectIds (24-character hex strings)
- Example: `"507f1f77bcf86cd799439011"`
- Generated automatically by MongoDB
- Contain timestamp information and are globally unique
- Used in all API endpoints that reference entities by ID

## Response Format

All endpoints return responses in this format:
```json
{
  "success": true,
  "message": "Success message",
  "data": { ... },
  "timestamp": "2024-08-16T10:30:00"
}
```

## Pagination Response Format

Paginated endpoints return:
```json
{
  "success": true,
  "message": "Success",
  "data": {
    "content": [...],
    "page": 0,
    "size": 10,
    "totalElements": 25,
    "totalPages": 3,
    "hasNext": true,
    "hasPrevious": false
  },
  "timestamp": "2024-08-16T10:30:00"
}
```

## Running the Services

### Prerequisites
- Java 21
- Maven 3.6+
- MongoDB (local or Docker)

### Build and Run

1. **Start MongoDB:**
   ```bash
   # Using Docker (recommended)
   docker run -d -p 27017:27017 --name mongodb mongo:latest
   
   # Or start your local MongoDB installation
   mongod --dbpath /path/to/your/db
   ```

2. **Build all modules:**
   ```bash
   mvn clean compile
   ```

3. **Run Quote Service:**
   ```bash
   cd quote-service
   mvn mn:run -Dmicronaut.environments=dev
   ```

4. **Run Joke Service (in another terminal):**
   ```bash
   cd joke-service
   mvn mn:run -Dmicronaut.environments=dev
   ```

The services will connect to MongoDB and be ready to accept requests.

## Key Features

- **Multi-module architecture** with shared common module
- **MongoDB integration** with Micronaut Data auto-implemented repositories
- **Reactive MongoDB drivers** for high performance
- **String-based ObjectIds** for proper MongoDB integration
- **Comprehensive validation** with Bean Validation
- **Global exception handling** with proper HTTP status codes
- **CORS enabled** for frontend integration
- **Pagination support** with MongoDB-native pagination
- **Like/unlike functionality** for user engagement
- **Approval workflow** for joke moderation with proper filtering
- **Consistent API responses** with standardized format
- **Flexible querying** with method-name-based and custom MongoDB queries

## Data Model

### Collections in MongoDB:
- `users` - User accounts and profiles
- `jokes` - Jokes with approval workflow and likes
- `quotes` - Inspirational quotes with author attribution and likes

### Key Fields:
- All entities use MongoDB ObjectId (`String` type in Java)
- Timestamps are stored as `LocalDateTime`
- Boolean flags for approval states (jokes only)
- Integer counters for likes
- String references between collections (userId)

## Frontend Integration Ready

The APIs are designed to be frontend-friendly with:
- CORS enabled for cross-origin requests
- Consistent JSON response format with proper structure
- MongoDB ObjectId handling (24-char hex strings)
- Proper HTTP status codes for all scenarios
- Detailed validation error messages
- Rich pagination metadata for UI components
- User-friendly success and error messages
- RESTful URL patterns with resource-based routing