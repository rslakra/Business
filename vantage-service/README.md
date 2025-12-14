# vantage-service

**Vantage Service** is a Spring Boot-based business management application that provides a comprehensive platform for managing various business entities including users, roles, tasks, marketing campaigns, cities, content taxonomies, and orders. The application follows a layered architecture pattern with clear separation of concerns.

## Overview

The application provides both web-based UI (using Thymeleaf) and RESTful API endpoints for managing business entities. It uses Spring Boot 3.5.7 with Java 21, and supports both H2 (development) and MySQL (production) databases.

> **📋 Release Notes**: See [RELEASE.md](RELEASE.md) for detailed upgrade information and migration guide.

## Technology Stack

### Core Framework
- **Spring Boot**: 3.5.7
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
- **Templating Engine**: Thymeleaf (managed by Spring Boot) with Layout Dialect 3.4.0
- **CSS Framework**: Bootstrap 5.3.7
- **JavaScript Libraries**:
  - jQuery 3.7.1
  - Font Awesome 4.7.0

### Utility Libraries
- **Lombok**: 1.18.36 (Code generation)
- **Apache Commons**: Commons Lang3 3.19.0
- **CSV Processing**: OpenCSV 5.10
- **Excel Processing**: Apache POI 5.4.1

## Architecture Layers

### 1. Presentation Layer
- **Web Controllers**: Handle HTTP requests and render Thymeleaf views
- **REST Controllers**: Provide RESTful API endpoints
- **Location**: `com.rslakra.vantageservice.*.controller`

### 2. Service Layer
- **Business Logic**: Service interfaces with implementations
- **Location**: `com.rslakra.vantageservice.*.service`
- **Key Services**: UserService, RoleService, TaskService, CityService, MarketingService, OrderService

### 3. Persistence Layer
- **Entities**: JPA entity classes representing database tables
- **Repositories**: Spring Data JPA repositories
- **Location**: `com.rslakra.vantageservice.*.persistence`

### 4. Data Layer
- **H2 Database**: Development/Testing
- **MySQL**: Production
- **Liquibase**: Schema migration and version control

## Key Features

1. **User & Role Management**: User CRUD operations, role-based access control, permission management
2. **Task Management**: Task creation, tracking, grouping, and filtering
3. **Marketing Campaign Management**: Campaign creation and tracking
4. **City Management**: City data management with bulk upload/download (CSV/Excel)
5. **Content Taxonomy**: Hierarchical content organization
6. **Order Management**: Order processing and status management
7. **Document Management**: Document storage with version control
8. **Data Import/Export**: CSV and Excel import/export functionality

## API Endpoints

### REST API Base Path
`/vantage-service/rest`

### Available REST Endpoints
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

## Application Configuration

### Server Configuration
- **Port**: 8080
- **Context Path**: `/vantage-service`
- **Base URL**: `http://localhost:8080/vantage-service`

### Database Configuration
- **H2 Database** (Development): `jdbc:h2:file:~/Downloads/H2DB/VantageService`
- **H2 Console**: `/h2`
- **MySQL** (Production): Configured via `application.properties`

### Security Configuration
- **Spring Security**: Auto-configuration is disabled for development
- **Configuration**: Set in `application.properties` via `spring.autoconfigure.exclude`
- **Note**: If security is required, create a `SecurityConfig` class in the `config` package to configure authentication and authorization properly

## Folder Structure

```
/
├── src                             # The src folder
│   ├── main/
│   │   ├── java/                   # Java source code
│   │   │   └── com/rslakra/vantageservice/
│   │   │       ├── account/        # User and role management
│   │   │       ├── city/            # City data management
│   │   │       ├── task/             # Task management
│   │   │       ├── marketing/       # Marketing campaign management
│   │   │       ├── order/            # Order processing
│   │   │       ├── dsp/              # Demand-Side Platform components
│   │   │       ├── document/        # Document management
│   │   │       ├── product/         # Product management
│   │   │       ├── project/         # Project management
│   │   │       ├── report/          # Reporting
│   │   │       ├── process/         # Process management
│   │   │       ├── home/            # Home page
│   │   │       └── common/         # Shared utilities
│   │   └── resources/
│   │       ├── templates/          # Thymeleaf templates
│   │       ├── static/             # CSS, JS, images
│   │       └── liquibase/          # Database migrations
│   └── test/                       # Test source code
├── buildMaven.sh                   # The Maven Build Script
├── pom.xml                         # Maven Project Object Model
├── runMaven.sh                     # The Maven Run Script
├── backupH2Database.sh            # H2 Database Backup Script
├── README.md                       # This file
├── RELEASE.md                      # Release Notes & Upgrade Guide
└── robots.txt                      # Search engine crawler configuration
```

## Database Tables

```shell
select * from addresses;
select * from audit_logs;
select * from content_taxonomies;
select * from documents;
select * from documents_versions;
select * from documents_permissions;
select * from marketing;
select * from permissions;
select * from roles;
select * from sessions;
select * from tasks;
select * from users;
select * from users_roles;
select * from user_security;
```

## Build & Deployment

### Build Configuration
- **Maven Compiler Plugin**: 3.13.0
- **Java Source/Target**: 21
- **Final Name**: `vantage-service`
- **Packaging**: JAR (executable)

### Build Scripts
- **buildMaven.sh**: Automated build script with version management
- **runMaven.sh**: Quick run script
- **backupH2Database.sh**: H2 database backup and restore utility

### Running the Application
```bash
# Build the application
./buildMaven.sh

# Run the application
./runMaven.sh
```

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
- **Note**: H2 database files from older versions may need to be recreated after Spring Boot upgrade. Use `backupH2Database.sh` to backup data.

### Frontend Development
- Use Thymeleaf fragments for reusable components
- Follow Bootstrap 5.3.7 conventions
- Maintain responsive design principles
- Use semantic HTML

## Module Structure

```
vantage-service/
├── account/          # User and role management
├── city/             # City data management
├── task/             # Task management
├── marketing/        # Marketing campaign management
├── order/            # Order processing
├── dsp/              # Demand-Side Platform components
│   ├── advertiser/  # Advertiser management
│   ├── campaign/     # Campaign management
│   ├── creative/     # Creative assets
│   ├── impression/   # Impression tracking
│   ├── taxonomy/     # Content taxonomy
│   └── transaction/  # Transaction management
├── document/         # Document management
├── product/          # Product management
├── project/          # Project management
├── report/           # Reporting
├── process/          # Process management
├── home/             # Home page
└── common/           # Shared utilities
```

## References

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Thymeleaf Documentation](https://www.thymeleaf.org/)
- [Liquibase Documentation](https://www.liquibase.org/)
- [Bootstrap 5 Documentation](https://getbootstrap.com/docs/5.3/)
- [RELEASE.md](RELEASE.md) - Detailed release notes and upgrade guide

## Author

**Rohtash Lakra**

---

*Last Updated: November 2025*  
*Spring Boot Version: 3.5.7*
