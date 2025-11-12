# Release Notes

## Version 3.5.7 Upgrade (November 2024)

### Major Changes

#### Spring Boot Upgrade
- **Upgraded from**: Spring Boot 2.7.18
- **Upgraded to**: Spring Boot 3.5.7
- **Impact**: Major version upgrade requiring Jakarta EE migration

#### Jakarta Persistence Migration
- **Migration**: All `javax.persistence.*` imports migrated to `jakarta.persistence.*`
- **Reason**: Spring Boot 3.x uses Jakarta EE 9+ specifications instead of Java EE
- **Files Updated**: All entity classes across the project:
  - `account/persistence/entity/User.java`
  - `account/persistence/entity/Role.java`
  - `account/persistence/entity/Person.java`
  - `task/persistence/entity/Task.java`
  - `task/persistence/entity/TaskGroup.java`
  - `marketing/persistence/entity/Marketing.java`
  - `advertising/persistence/entity/ContentTaxonomy.java`
  - `product/persistence/entity/Product.java`
  - `project/persistence/entity/Project.java`
  - `project/persistence/entity/Feature.java`
  - `order/persistence/entity/Transaction.java`
  - `process/persistence/entity/Schema.java`
  - `report/persistence/entity/Report.java`

### Dependency Updates

#### Core Dependencies
- **Lombok**: 1.18.34 → 1.18.36
- **JUnit Jupiter Params**: 5.10.2 → 5.11.4
- **Liquibase**: 4.32.0 (maintained)
- **MySQL Connector**: 9.2.0 (maintained)

#### Utility Libraries
- **Apache POI**: 5.4.1 (maintained)
- **Apache POI OOXML**: 5.4.1 (maintained)
- **OpenCSV**: 5.10 (maintained)
- **Commons CSV**: 1.14.0 (maintained)
- **Commons Lang3**: 3.18.0 (maintained)

#### Maven Plugins
- **Maven Compiler Plugin**: 3.13.0
- **Maven Checkstyle Plugin**: 3.5.0
- **Maven Release Plugin**: 3.1.1

### Configuration Changes

#### Application Properties
- **Test Configuration**: Fixed Liquibase changelog path in `src/test/resources/application.properties`
  - Changed from: `classpath:db/changelog/dbchangelog.xml`
  - Changed to: `classpath:liquibase/master_changelog.xml`

### Build System

#### Java Version
- **Java**: 21
- **Maven Compiler Source/Target**: 21

#### Build Script
- Build script (`buildMaven.sh`) remains compatible
- All builds successful with new dependencies

### Migration Guide

#### For Developers Upgrading from Spring Boot 2.x

1. **Update Imports**
   ```java
   // Before (Spring Boot 2.x)
   import javax.persistence.Entity;
   import javax.persistence.Column;
   import javax.persistence.Table;
   
   // After (Spring Boot 3.x)
   import jakarta.persistence.Entity;
   import jakarta.persistence.Column;
   import jakarta.persistence.Table;
   ```

2. **Check Dependency Compatibility**
   - Ensure all third-party libraries support Jakarta EE 9+
   - Update any custom dependencies that may still use `javax.*` packages

3. **Review Configuration**
   - Check `application.properties` for any deprecated properties
   - Verify Liquibase changelog paths are correct
   - Review security configurations if applicable

4. **Testing**
   - Run all tests to ensure compatibility
   - Update test configurations if needed
   - Verify database migrations work correctly

### Known Issues

#### Maven Surefire Plugin Warning
- **Issue**: `IncompatibleClassChangeError` warning when reporting test failures
- **Status**: Cosmetic warning only - does not affect test execution
- **Impact**: None - tests run successfully, warning appears only during failure reporting
- **Workaround**: None required - this is a known issue with Maven Surefire 3.5.x and JUnit Platform

### Testing

- All compilation successful
- Build process completes successfully
- Tests execute (some test failures are application-specific, not build-related)

### Breaking Changes

- **Jakarta Persistence**: All code using `javax.persistence` must be updated to `jakarta.persistence`
- **Java Version**: Requires Java 17+ (project uses Java 21)

### Compatibility

- **Backward Compatibility**: Not compatible with Spring Boot 2.x codebase
- **Database**: No database schema changes required for this upgrade
- **API**: No API changes in this release

---

## Previous Versions

### Version 2.7.18 (Previous)
- Spring Boot 2.7.18
- Java EE (`javax.persistence`)
- Java 21

---

## References

- [Spring Boot 3.0 Migration Guide](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-3.0-Migration-Guide)
- [Jakarta EE Migration](https://jakarta.ee/specifications/persistence/3.0/)
- [Spring Boot 3.5.7 Release Notes](https://github.com/spring-projects/spring-boot/releases/tag/v3.5.7)

