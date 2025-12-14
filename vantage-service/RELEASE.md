# Release Notes

## Version 3.5.7 Upgrade (November 2025)

### Major Upgrade: Spring Boot 2.7.18 → 3.5.7

This release includes a major upgrade from Spring Boot 2.x to 3.x, which brings significant improvements, new features, and requires Jakarta EE migration.

---

## Upgrade Summary

### Core Framework Upgrade
- **Spring Boot**: `2.7.18` → `3.5.7`
- **Spring Framework**: Upgraded to `6.2.12` (via Spring Boot 3.5.7)
- **Java**: `21` (compatible, no change required)

### Breaking Changes

#### 1. Jakarta EE Migration
Spring Boot 3.x uses Jakarta EE instead of Java EE. All `javax.*` packages have been migrated to `jakarta.*`:

- **javax.persistence** → **jakarta.persistence** (21 files migrated)
- **javax.annotation** → **jakarta.annotation** (1 file migrated)

**Files Updated:**
- All entity classes in `com.rslakra.vantageservice.*.persistence.entity`
- `CitiesInitializer.java`

#### 2. Dependency Updates

**Updated Dependencies:**
- **Lombok**: `1.18.34` → `1.18.36`
- **Commons Lang3**: `3.18.0` → `3.19.0` (managed by Spring Boot)
- **JUnit Jupiter Params**: `5.10.2` → `5.11.3`

**Maintained Versions:**
- **Thymeleaf Layout Dialect**: `3.4.0`
- **Liquibase**: `4.32.0`
- **MySQL Connector**: `9.2.0`
- **Apache POI**: `5.4.1`
- **OpenCSV**: `5.10`

### Configuration Changes

#### Lombok Configuration
- Set Lombok scope to `provided` (was commented out)
- Added explicit annotation processor path in `maven-compiler-plugin`
- Configured Spring Boot plugin to exclude Lombok from final JAR

#### Maven Compiler Plugin
- Added annotation processor configuration for Lombok
- Added `-parameters` compiler argument

### Code Changes

#### CitiesInitializer.java
- Fixed optimistic locking issue by clearing entity IDs before creation
- Fixed logger debug statement syntax

#### OrderDetail.java
- Fixed Checkstyle violation: renamed `unit_price` → `unitPrice`

---

## Migration Guide

### For Developers

1. **Update IDE Settings**
   - Ensure your IDE supports Jakarta EE
   - Update import statements if using code completion

2. **Database Migration**
   - H2 database files from older versions may need to be recreated
   - Use `backupH2Database.sh` script to backup data before upgrading
   - Liquibase will automatically recreate schema on first run

3. **Testing**
   - All existing tests should work with minimal changes
   - Verify Jakarta EE imports in any custom code

### Known Issues & Solutions

#### H2 Database Version Compatibility
**Issue**: H2 database files created with older versions (format 2) are incompatible with H2 2.3.232 (format 3).

**Solution**: 
- Use `backupH2Database.sh` to export data
- Delete old database files
- Let Liquibase recreate schema
- Restore data if needed using the backup script

#### External Dependencies
- `appsuite-spring` version `0.0.30` - Ensure this version is compatible with Spring Boot 3.5.7
- `appsuite-core` version `0.0.74` - Verify compatibility

---

## New Features & Improvements

### Spring Boot 3.5.7 Features
- Enhanced performance and security
- Improved observability
- Better native image support
- Updated dependency management

### Hibernate 6.6.33
- Better Jakarta EE support
- Performance improvements
- Enhanced query capabilities

---

## Build & Deployment

### Build Requirements
- **Java**: 21
- **Maven**: 3.x
- **Spring Boot**: 3.5.7

### Build Commands
```bash
# Clean build
./buildMaven.sh

# Run application
./runMaven.sh
```

### Database Backup Script
```bash
# Backup H2 database before upgrade
./backupH2Database.sh
```

---

## Testing

### Verified Functionality
- ✅ Application starts successfully
- ✅ Database connections working
- ✅ Liquibase migrations execute correctly
- ✅ All endpoints registered
- ✅ Cities initialization working
- ✅ Build completes successfully

### Test Results
- Compilation: **SUCCESS** (101 source files)
- Checkstyle: **0 violations**
- Application startup: **SUCCESS**

---

## Rollback Plan

If you need to rollback to Spring Boot 2.7.18:

1. Revert `pom.xml` changes
2. Revert all `jakarta.*` → `javax.*` imports (21 files)
3. Revert Lombok configuration changes
4. Restore previous H2 database files from backup

---

## Additional Notes

### Scripts Added
- `backupH2Database.sh` - H2 database backup and restore utility

### Files Modified
- `pom.xml` - Updated Spring Boot and dependency versions
- 21 entity files - Migrated to Jakarta EE
- `CitiesInitializer.java` - Fixed entity ID handling
- `OrderDetail.java` - Fixed naming convention

---

## References

- [Spring Boot 3.5.7 Release Notes](https://spring.io/blog/2025/10/23/spring-boot-3-5-7-available-now)
- [Spring Boot 3.0 Migration Guide](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-3.0-Migration-Guide)
- [Jakarta EE Migration Guide](https://jakarta.ee/specifications/platform/9/)

---

## Support

For issues related to this upgrade, please check:
1. Application logs for specific error messages
2. Database compatibility (H2 version)
3. External dependency versions (`appsuite-spring`, `appsuite-core`)

---

*Release Date: November 2025*  
*Upgraded by: Automated Migration*

