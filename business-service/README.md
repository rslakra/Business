# business-service

The ```business-service``` contains business management project.

## Technology Stack

- **Spring Boot**: 3.5.7
- **Java**: 21
- **Build Tool**: Maven
- **Persistence**: Jakarta Persistence API (JPA)
- **Database Migration**: Liquibase 4.32.0
- **Testing**: JUnit 5.11.4

## Folder Structure Conventions

---

```
/
├── src                             # The src folder
├── buildMaven.sh                   # The Maven Build Script
├── pom.xml                         # A Project Object Model or POM is XML file that contains information about the 
project and configuration details used by Maven to build the project.
├── runMaven.sh                     # The Maven Build Script
├── README.md
├── <module>                        # The module service
└── /
```

## Tables

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

## Build

To build the project, use the provided build script:

```shell
./buildMaven.sh
```

This script will:
- Clean and compile the project
- Run tests (if enabled)
- Package the application as a JAR file

## Recent Changes

For detailed release notes and upgrade information, see [RELEASES.md](RELEASES.md).

### Latest Release (v3.5.7)
- Upgraded Spring Boot from 2.7.18 to 3.5.7
- Migrated to Jakarta Persistence API
- Updated dependencies and configurations
- Java 21 support

# Reference

---

# Author

---

- Rohtash Lakra
