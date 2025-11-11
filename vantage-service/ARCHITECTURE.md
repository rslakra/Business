# Vantage Service - Architecture Documentation

## Overview

**Vantage Service** is a Spring Boot-based business management application that provides a comprehensive platform for managing various business entities including users, roles, tasks, marketing campaigns, cities, content taxonomies, and orders. The application follows a layered architecture pattern with clear separation of concerns.

## Architecture Diagrams

### 1. High-Level System Architecture

```mermaid
graph TB
    subgraph "Client Layer"
        Browser[Web Browser]
        API_Client[REST API Client]
    end
    
    subgraph "Application Layer"
        Web_Controllers[Web Controllers<br/>Thymeleaf Views]
        REST_Controllers[REST Controllers<br/>JSON APIs]
    end
    
    subgraph "Business Layer"
        Services[Service Layer<br/>Business Logic]
    end
    
    subgraph "Data Access Layer"
        Repositories[Repository Layer<br/>Spring Data JPA]
        Entities[JPA Entities]
    end
    
    subgraph "Data Layer"
        H2[(H2 Database<br/>Development)]
        MySQL[(MySQL Database<br/>Production)]
        Liquibase[Liquibase<br/>Schema Migration]
    end
    
    Browser --> Web_Controllers
    API_Client --> REST_Controllers
    Web_Controllers --> Services
    REST_Controllers --> Services
    Services --> Repositories
    Repositories --> Entities
    Entities --> H2
    Entities --> MySQL
    Liquibase --> H2
    Liquibase --> MySQL
```

### 2. Layered Architecture

```mermaid
graph TD
    subgraph "Presentation Layer"
        A1[Web Controllers<br/>Thymeleaf Templates]
        A2[REST Controllers<br/>JSON Responses]
        A3[Static Resources<br/>CSS, JS, Images]
    end
    
    subgraph "Service Layer"
        B1[User Service]
        B2[Role Service]
        B3[Task Service]
        B4[City Service]
        B5[Order Service]
        B6[Marketing Service]
    end
    
    subgraph "Persistence Layer"
        C1[User Repository]
        C2[Role Repository]
        C3[Task Repository]
        C4[City Repository]
        C5[Order Repository]
        C6[Marketing Repository]
    end
    
    subgraph "Data Layer"
        D1[(Database)]
    end
    
    A1 --> B1
    A1 --> B2
    A1 --> B3
    A2 --> B1
    A2 --> B4
    A2 --> B5
    B1 --> C1
    B2 --> C2
    B3 --> C3
    B4 --> C4
    B5 --> C5
    B6 --> C6
    C1 --> D1
    C2 --> D1
    C3 --> D1
    C4 --> D1
    C5 --> D1
    C6 --> D1
```

### 3. Request Flow Diagram

```mermaid
sequenceDiagram
    participant Client
    participant Controller
    participant Service
    participant Repository
    participant Database
    
    Client->>Controller: HTTP Request
    Controller->>Service: Business Logic Call
    Service->>Repository: Data Access
    Repository->>Database: SQL Query
    Database-->>Repository: Result Set
    Repository-->>Service: Entity Objects
    Service-->>Controller: Business Objects
    Controller-->>Client: HTTP Response (View/JSON)
```

### 4. Module Structure Diagram

```mermaid
graph LR
    subgraph "Vantage Service"
        A[Account Module]
        C[City Module]
        T[Task Module]
        M[Marketing Module]
        O[Order Module]
        D[DSP Module]
        DOC[Document Module]
        P[Product Module]
        PRJ[Project Module]
        R[Report Module]
        PROC[Process Module]
        H[Home Module]
        COM[Common Module]
    end
    
    A --> COM
    C --> COM
    T --> COM
    M --> COM
    O --> COM
    D --> COM
    DOC --> COM
    P --> COM
    PRJ --> COM
    R --> COM
    PROC --> COM
    H --> COM
```

### 5. Database Schema Diagram

```mermaid
erDiagram
    USERS ||--o{ USERS_ROLES : has
    ROLES ||--o{ USERS_ROLES : assigned_to
    USERS ||--o{ USER_SECURITY : has
    USERS ||--o{ SESSIONS : creates
    USERS ||--o{ ADDRESSES : has
    USERS ||--o{ ORDERS : places
    ORDERS ||--o{ ORDER_DETAILS : contains
    ROLES ||--o{ PERMISSIONS : has
    DOCUMENTS ||--o{ DOCUMENTS_VERSIONS : has
    DOCUMENTS ||--o{ DOCUMENTS_PERMISSIONS : has
    
    USERS {
        bigint id PK
        string first_name
        string last_name
        string email
        timestamp created_on
    }
    
    ROLES {
        bigint id PK
        string name
        string description
    }
    
    USERS_ROLES {
        bigint user_id FK
        bigint role_id FK
    }
    
    USER_SECURITY {
        bigint id PK
        bigint user_id FK
        string platform
        string device_token
        string hashed_token
    }
    
    CITIES {
        bigint id PK
        string name
        int founded_in
        bigint population
    }
    
    TASKS {
        bigint id PK
        string name
        string description
        string status
    }
    
    ORDERS {
        bigint id PK
        bigint user_id FK
        string status
        decimal total
    }
    
    ORDER_DETAILS {
        bigint id PK
        bigint order_id FK
        bigint product_id
        int quantity
        decimal price
    }
    
    MARKETING {
        bigint id PK
        string name
        string description
        string status
    }
    
    CONTENT_TAXONOMIES {
        bigint id PK
        string name
        string path
        bigint parent_id FK
    }
    
    DOCUMENTS {
        bigint id PK
        string name
        string type
        string content
    }
    
    AUDIT_LOGS {
        bigint id PK
        string entity_type
        bigint entity_id
        string action
        string changed_by
        timestamp changed_on
    }
```

### 6. Component Interaction Diagram

```mermaid
graph TB
    subgraph "Frontend"
        UI[Thymeleaf Templates]
        JS[JavaScript/jQuery]
        CSS[Bootstrap CSS]
    end
    
    subgraph "Backend - Controllers"
        WC[Web Controllers]
        RC[REST Controllers]
    end
    
    subgraph "Backend - Services"
        US[User Service]
        RS[Role Service]
        TS[Task Service]
        CS[City Service]
        OS[Order Service]
        MS[Marketing Service]
    end
    
    subgraph "Backend - Data Access"
        UR[User Repository]
        RR[Role Repository]
        TR[Task Repository]
        CR[City Repository]
        OR[Order Repository]
        MR[Marketing Repository]
    end
    
    subgraph "Infrastructure"
        JPA[Spring Data JPA]
        LIQ[Liquibase]
        DB[(Database)]
    end
    
    UI --> WC
    JS --> WC
    CSS --> UI
    WC --> US
    WC --> RS
    WC --> TS
    RC --> CS
    RC --> OS
    RC --> MS
    
    US --> UR
    RS --> RR
    TS --> TR
    CS --> CR
    OS --> OR
    MS --> MR
    
    UR --> JPA
    RR --> JPA
    TR --> JPA
    CR --> JPA
    OR --> JPA
    MR --> JPA
    
    JPA --> DB
    LIQ --> DB
```

### 7. Security & Authentication Flow

```mermaid
sequenceDiagram
    participant User
    participant Browser
    participant AuthController
    participant UserService
    participant UserRepository
    participant Session
    participant Database
    
    User->>Browser: Access Protected Resource
    Browser->>AuthController: GET /auth/login
    AuthController->>Browser: Render Login Page
    User->>Browser: Submit Credentials
    Browser->>UserService: Authenticate
    UserService->>UserRepository: Find User
    UserRepository->>Database: Query
    Database-->>UserRepository: User Data
    UserRepository-->>UserService: User Entity
    UserService->>Session: Create Session
    Session->>Database: Store Session
    UserService-->>Browser: Authentication Success
    Browser->>User: Redirect to Home
```

### 8. Data Import/Export Flow

```mermaid
graph LR
    subgraph "Import Flow"
        A1[Upload File<br/>CSV/Excel]
        A2[Parser<br/>CSV/Excel Parser]
        A3[Service Layer]
        A4[Repository]
        A5[(Database)]
    end
    
    subgraph "Export Flow"
        B1[Request Export]
        B2[Service Layer]
        B3[Repository]
        B4[(Database)]
        B5[Parser<br/>CSV/Excel Generator]
        B6[Download File]
    end
    
    A1 --> A2
    A2 --> A3
    A3 --> A4
    A4 --> A5
    
    B1 --> B2
    B2 --> B3
    B3 --> B4
    B4 --> B5
    B5 --> B6
```

### 9. Build & Deployment Pipeline

```mermaid
graph TD
    A[Source Code] --> B[Maven Build]
    B --> C{Checkstyle}
    C -->|Pass| D[Compile]
    C -->|Fail| E[Build Failed]
    D --> F[Run Tests]
    F --> G{Tests Pass?}
    G -->|Yes| H[Package JAR]
    G -->|No| E
    H --> I[Liquibase Migration]
    I --> J[Deploy to Server]
    J --> K[Start Application]
    K --> L[Application Running]
```

### 10. Technology Stack Diagram

```mermaid
graph TB
    subgraph "Frontend Stack"
        F1[Thymeleaf 3.0.1]
        F2[Bootstrap 5.3.5]
        F3[jQuery 3.4.1]
        F4[Font Awesome 4.7.0]
    end
    
    subgraph "Backend Stack"
        B1[Spring Boot 2.7.18]
        B2[Spring MVC]
        B3[Spring Data JPA]
        B4[Spring AOP]
    end
    
    subgraph "Data Stack"
        D1[H2 Database]
        D2[MySQL 9.2.0]
        D3[Liquibase 4.32.0]
        D4[HikariCP]
    end
    
    subgraph "Utility Stack"
        U1[Lombok 1.18.34]
        U2[Apache POI 5.4.1]
        U3[OpenCSV 5.10]
        U4[Commons Lang3 3.18.0]
    end
    
    F1 --> B1
    F2 --> F1
    F3 --> F1
    B1 --> B2
    B1 --> B3
    B1 --> B4
    B3 --> D1
    B3 --> D2
    D3 --> D1
    D3 --> D2
    B3 --> D4
```

## Technology Stack

### Core Framework
- **Spring Boot**: 2.7.18
- **Java**: 21
- **Build Tool**: Maven 3.x
- **Packaging**: JAR

### Backend Technologies
- **Spring Framework**
  - Spring Boot Starter Web (RESTful APIs)
  - Spring Boot Starter Data JPA (Database persistence)
  - Spring Boot Starter AOP (Aspect-oriented programming)
  - Spring Boot Starter Thymeleaf (Server-side templating)
- **Database**
  - H2 Database (Development/Testing)
  - MySQL 9.2.0 (Production-ready)
- **ORM**: Hibernate (via Spring Data JPA)
- **Database Migration**: Liquibase 4.32.0
- **Connection Pooling**: HikariCP

### Frontend Technologies
- **Templating Engine**: Thymeleaf 3.0.1 with Layout Dialect 3.4.0
- **CSS Framework**: Bootstrap 5.3.5
- **JavaScript Libraries**:
  - jQuery 3.4.1
  - jQuery UI 1.12.1
  - X-Editable (Bootstrap 3 compatible)
  - Font Awesome 4.7.0

### Utility Libraries
- **Lombok**: 1.18.34 (Code generation)
- **Apache Commons**: 
  - Commons Lang3 3.18.0
  - Commons CSV 1.14.0
- **CSV Processing**: OpenCSV 5.10
- **Excel Processing**: Apache POI 5.4.1 (POI & POI-OOXML)

### Custom Dependencies
- **appsuite-core**: 0.0.74 (Custom core utilities)
- **appsuite-spring**: 0.0.26 (Custom Spring integrations)

### Testing
- **JUnit Jupiter**: 5.10.2 (with parameters)
- **Spring Boot Starter Test**

### Code Quality
- **Checkstyle**: 3.5.0 (Code style enforcement)
- **Maven Checkstyle Plugin**: Integrated with remote code styles repository

## Architecture Layers

### 1. Presentation Layer

#### Web Controllers
- **Location**: `com.rslakra.vantageservice.*.controller`
- **Purpose**: Handle HTTP requests and render views
- **Pattern**: MVC (Model-View-Controller)
- **Key Controllers**:
  - `AccountController` - Account management
  - `AuthController` - Authentication
  - `CityWebController` - City management
  - `TaskWebController` - Task management
  - `MarketingWebController` - Marketing campaign management
  - `OrderWebController` - Order management
  - `RoleWebController` - Role management
  - `UserWebController` - User management
  - `ContentTaxonomyWebController` - Content taxonomy management

#### REST Controllers
- **Location**: `com.rslakra.vantageservice.*.controller.rest`
- **Purpose**: Provide RESTful API endpoints
- **Base Class**: `AbstractRestController<T, ID>`
- **Key REST Controllers**:
  - `UserController` - `/rest/users`
  - `RoleController` - `/rest/roles`
  - `TaskController` - `/rest/tasks`
  - `OrderController` - `/rest/orders`
  - `MarketingController` - `/rest/marketing`
  - `ContentTaxonomyController` - `/rest/content-taxonomy`

#### View Templates
- **Location**: `src/main/resources/templates/`
- **Template Engine**: Thymeleaf with Layout Dialect
- **Structure**:
  ```
  templates/
  ├── layouts/
  │   └── default.html          # Main layout template
  ├── fragments/
  │   ├── header.html           # Page header
  │   ├── navigation.html       # Navigation bar
  │   ├── footer.html           # Page footer
  │   ├── bodyScripts.html      # JavaScript includes
  │   └── pageTitle.html        # Page title fragment
  ├── views/                    # Feature-specific views
  │   ├── account/              # User & role management
  │   ├── city/                 # City management
  │   ├── task/                 # Task management
  │   ├── marketing/            # Marketing management
  │   └── dsp/                  # DSP (Demand-Side Platform) views
  ├── login.html                # Login page
  ├── home.html                 # Home page
  └── 403.html                  # Access denied page
  ```

### 2. Service Layer

- **Location**: `com.rslakra.vantageservice.*.service`
- **Purpose**: Business logic implementation
- **Pattern**: Service interface with implementation
- **Key Services**:
  - `UserService` / `UserServiceImpl`
  - `RoleService` / `RoleServiceImpl`
  - `TaskService` / `TaskServiceImpl`
  - `CityService` / `CityServiceImpl`
  - `MarketingService` / `MarketingServiceImpl`
  - `OrderService` / `OrderServiceImpl`
  - `ContentTaxonomyService` / `ContentTaxonomyServiceImpl`

### 3. Persistence Layer

#### Entities
- **Location**: `com.rslakra.vantageservice.*.persistence.entity`
- **Purpose**: JPA entity classes representing database tables
- **Key Entities**:
  - `User`, `Role`, `Person`
  - `City`
  - `Task`, `TaskGroup`
  - `Marketing`
  - `Order`, `OrderDetail`
  - `ContentTaxonomy`
  - `Document`, `DocumentVersion`
  - `Address`
  - `AuditLog`

#### Repositories
- **Location**: `com.rslakra.vantageservice.*.persistence.repository`
- **Purpose**: Data access layer using Spring Data JPA
- **Pattern**: Repository interface extending `JpaRepository<T, ID>`
- **Key Repositories**:
  - `UserRepository`
  - `RoleRepository`
  - `CityRepository`
  - `TaskRepository`
  - `MarketingRepository`
  - `OrderRepository`
  - `ContentTaxonomyRepository`

### 4. Data Transfer & Filtering

#### Filters
- **Location**: `com.rslakra.vantageservice.*.filter`
- **Purpose**: Query parameter filtering and validation
- **Key Filters**:
  - `UserFilter`
  - `RoleFilter`
  - `TaskFilter`
  - `OrderFilter`
  - `MarketingFilter`
  - `ContentTaxonomyFilter`

#### Parsers
- **Location**: `com.rslakra.vantageservice.*.parser`
- **Purpose**: Data parsing and transformation (CSV, Excel)
- **Key Parsers**:
  - `UserParser`
  - `RoleParser`
  - `TaskParser`
  - `OrderParser`
  - `ContentTaxonomyParser`

### 5. Configuration Layer

- **Location**: `com.rslakra.vantageservice.config`
- **Key Configuration Classes**:
  - `AuditJpaConfig` - JPA auditing configuration
  - `ExceptionHandlerConfig` - Global exception handling
  - `ViewConfiguration` - View resolver configuration
  - `StaticDataProperties` - Static data configuration

## Database Architecture

### Database Management
- **Migration Tool**: Liquibase
- **Changelog Location**: `src/main/resources/liquibase/`
- **Master Changelog**: `liquibase/master_changelog.xml`
- **Table Definitions**: `liquibase/changelog/tables/`
- **Initial Data**: `liquibase/changelog/sqls/`

### Database Schema

#### Core Tables
- `users` - User accounts
- `roles` - User roles
- `users_roles` - User-role mapping
- `permissions` - System permissions
- `user_security` - Security tokens and authentication
- `sessions` - User sessions
- `audit_logs` - Audit trail

#### Business Tables
- `cities` - City information
- `tasks` - Task management
- `marketing` - Marketing campaigns
- `orders` - Order management
- `order_details` - Order line items
- `content_taxonomies` - Content taxonomy structure

#### Document Management
- `documents` - Document storage
- `documents_versions` - Document versioning
- `documents_permissions` - Document access control

#### Address Management
- `addresses` - Address information

### Database Support
- **Primary**: H2 (File-based, for development)
- **Production**: MySQL 9.2.0
- **Connection Pool**: HikariCP (max pool size: 2)

## Application Configuration

### Server Configuration
- **Port**: 8080
- **Context Path**: `/vantage-service`
- **Management Port**: 8081 (Actuator endpoints)
- **Base URL**: `http://localhost:8080/vantage-service`

### API Configuration
- **REST API Prefix**: `/rest`
- **API Version Prefix**: `/v1`
- **Example**: `/vantage-service/rest/users`

### Database Configuration
```properties
# H2 Database (Development)
spring.datasource.url=jdbc:h2:file:~/Downloads/H2DB/VantageService;AUTO_SERVER=TRUE;
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# H2 Console
spring.h2.console.enabled=true
spring.h2.console.path=/h2
```

### JPA Configuration
- **Open-in-View**: Disabled (prevents lazy loading issues)
- **Show SQL**: Enabled (development)
- **DDL Auto**: Managed by Liquibase

### Thymeleaf Configuration
- **Cache**: Disabled (development)
- **Template Location**: `classpath:/templates/`
- **Static Resources**: `classpath:/static/`

## Security Architecture

### Authentication & Authorization
- **Login Page**: `/auth/login`
- **Session Management**: Database-backed sessions
- **Role-Based Access Control (RBAC)**: Implemented via roles and permissions
- **Security Tables**:
  - `user_security` - Device tokens, hashed tokens, salt
  - `sessions` - Active user sessions
  - `users_roles` - User role assignments
  - `permissions` - System permissions

### Access Control
- **403 Page**: Custom access denied page
- **Filter Classes**: `RoleFilter`, `UserFilter` for data filtering

## Static Resources

### CSS
- **Location**: `src/main/resources/static/css/`
- **Files**:
  - `styles.css` - Main stylesheet
  - `form-styles.css` - Form styling
  - `table-styles.css` - Table styling
  - `navbar-styles.css` - Navigation styling
  - `variables.css` - CSS variables

### JavaScript
- **Location**: `src/main/resources/static/js/`
- **Files**:
  - `jsUtils.js` - Utility functions
  - `uiModal.js` - Modal dialog handling
  - `uiToggler.js` - UI toggle functionality

### Images & Fonts
- **Location**: `src/main/resources/static/`
- **Assets**:
  - `images/` - Logo, favicon
  - `fonts/` - Glyphicons

## Build & Deployment

### Build Configuration
- **Maven Compiler Plugin**: 3.13.0
- **Java Source/Target**: 21
- **Final Name**: `vantage-service`
- **Packaging**: JAR (executable)

### Build Scripts
- **buildMaven.sh**: Automated build script with version management
- **runMaven.sh**: Quick run script

### Version Management
- **Version Property**: `${revision}` (default: 0.0.0)
- **Version Strategy**: Git commit count-based versioning
- **Build Types**: SNAPSHOT and RELEASE versions

### Code Quality
- **Checkstyle**: Integrated with remote configuration
- **Config Location**: `https://raw.githubusercontent.com/rslakra/code-styles/master/styles.xml`
- **Threshold**: 0 violations allowed

## Logging

### Logging Framework
- **Framework**: Logback (via SLF4J)
- **Configuration**: `src/main/resources/logback.xml`

### Log Levels
- **Application**: DEBUG (`com.rslakra`)
- **Spring Framework**: WARN
- **Hibernate**: INFO
- **Liquibase**: WARN
- **Apache/Tomcat**: WARN

## Key Features

### 1. User & Role Management
- User CRUD operations
- Role-based access control
- Permission management
- User-role assignments

### 2. Task Management
- Task creation and tracking
- Task grouping
- Task filtering and search

### 3. Marketing Campaign Management
- Campaign creation and management
- Campaign tracking

### 4. City Management
- City data management
- Bulk upload/download (CSV/Excel)
- Inline editing

### 5. Content Taxonomy
- Hierarchical content organization
- Taxonomy management

### 6. Order Management
- Order processing
- Order details tracking
- Order status management

### 7. Document Management
- Document storage
- Version control
- Permission-based access

### 8. Data Import/Export
- CSV import/export
- Excel import/export
- Bulk operations

## Module Structure

```
vantage-service/
├── account/          # User and role management
├── city/             # City data management
├── task/             # Task management
├── marketing/         # Marketing campaign management
├── order/             # Order processing
├── dsp/               # Demand-Side Platform components
│   ├── advertiser/   # Advertiser management
│   ├── campaign/      # Campaign management
│   ├── creative/      # Creative assets
│   ├── impression/    # Impression tracking
│   ├── taxonomy/      # Content taxonomy
│   └── transaction/   # Transaction management
├── document/          # Document management
├── product/           # Product management
├── project/           # Project management
├── report/            # Reporting
├── process/           # Process management
├── home/              # Home page
└── common/            # Shared utilities
```

## API Endpoints

### REST API Base Path
`/vantage-service/rest`

### Available Endpoints
- `GET /rest/users` - List all users
- `GET /rest/users/filter` - Filter users
- `POST /rest/users` - Create user
- `PUT /rest/users` - Update user
- `GET /rest/roles` - List all roles
- `GET /rest/tasks` - List all tasks
- `GET /rest/orders` - List all orders
- `GET /rest/marketing` - List marketing campaigns
- `GET /rest/content-taxonomy` - List content taxonomies

### Web UI Endpoints
- `/auth/login` - Login page
- `/home` - Home page
- `/users/list` - User list
- `/roles/list` - Role list
- `/cities/list` - City list
- `/tasks/list` - Task list
- `/marketing/list` - Marketing list
- `/orders/list` - Order list

## Development Guidelines

### Code Style
- Follow Checkstyle rules from remote repository
- Use Lombok for boilerplate code reduction
- Maintain consistent package structure

### Database Changes
- Always use Liquibase for schema changes
- Create changesets in `liquibase/changelog/tables/`
- Include both XML and YAML formats when possible
- Test migrations on H2 before applying to MySQL

### Testing
- Write unit tests for services
- Use JUnit Jupiter with parameterized tests
- Test REST endpoints with integration tests

### Frontend Development
- Use Thymeleaf fragments for reusable components
- Follow Bootstrap 5.3.5 conventions
- Maintain responsive design principles
- Use semantic HTML

## Deployment Considerations

### Environment Configuration
- Use `application.properties` for environment-specific settings
- Configure database connection for production
- Enable Thymeleaf caching in production
- Set appropriate log levels

### Database Migration
- Liquibase runs automatically on application startup
- Ensure database credentials are properly configured
- Review migration scripts before deployment

### Performance
- HikariCP connection pool configured (max: 2)
- Consider increasing pool size for production
- Enable JPA query caching where appropriate
- Monitor SQL query performance

## Future Enhancements

### Potential Improvements
1. **Security**: Implement Spring Security for authentication/authorization
2. **API Documentation**: Add Swagger/OpenAPI documentation
3. **Caching**: Implement Redis for session and data caching
4. **Monitoring**: Add Actuator endpoints for health checks
5. **Testing**: Increase test coverage
6. **DSP Features**: Complete Demand-Side Platform implementation
7. **Real-time Features**: WebSocket support for real-time updates

## References

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Thymeleaf Documentation](https://www.thymeleaf.org/)
- [Liquibase Documentation](https://www.liquibase.org/)
- [Bootstrap 5 Documentation](https://getbootstrap.com/docs/5.3/)

## Author

**Rohtash Lakra**

---

*Last Updated: November 2024*

