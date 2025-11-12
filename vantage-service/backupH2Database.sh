#!/bin/bash
# H2 Database Backup and Restore Script
# This script exports data from an old H2 database and prepares for upgrade
# Author: Generated for vantage-service

set -e  # Exit on error

# Configuration - Update these if needed
DB_DIR="$HOME/Downloads/H2DB"
DB_NAME="VantageService"
DB_URL="jdbc:h2:file:${DB_DIR}/${DB_NAME}"
DB_USER="sa"
DB_PASSWORD=""
BACKUP_DIR="${DB_DIR}/backups"
TIMESTAMP=$(date +%Y%m%d_%H%M%S)
BACKUP_FILE="${BACKUP_DIR}/${DB_NAME}_backup_${TIMESTAMP}.sql"
H2_JAR_PATH=""

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo -e "${GREEN}=== H2 Database Backup Script ===${NC}"
echo ""

# Function to find H2 JAR
find_h2_jar() {
    echo "Searching for H2 JAR in Maven repository..."
    
    # Try to find H2 JAR in local Maven repository
    H2_VERSION=$(mvn help:evaluate -Dexpression=h2.version -q -DforceStdout 2>/dev/null || echo "")
    
    if [ -z "$H2_VERSION" ]; then
        # Try to get from Spring Boot parent
        H2_VERSION=$(mvn dependency:tree | grep -i "h2:jar" | head -1 | sed 's/.*h2:jar:\([^:]*\):.*/\1/' || echo "")
    fi
    
    if [ -z "$H2_VERSION" ]; then
        echo -e "${YELLOW}Warning: Could not determine H2 version automatically.${NC}"
        echo "Please provide the H2 JAR path manually."
        read -p "Enter H2 JAR path (or press Enter to use default location): " H2_JAR_PATH
        
        if [ -z "$H2_JAR_PATH" ]; then
            # Try default Maven location
            H2_JAR_PATH="$HOME/.m2/repository/com/h2database/h2/2.3.232/h2-2.3.232.jar"
        fi
    else
        echo "Found H2 version: $H2_VERSION"
        H2_JAR_PATH="$HOME/.m2/repository/com/h2database/h2/${H2_VERSION}/h2-${H2_VERSION}.jar"
    fi
    
    if [ ! -f "$H2_JAR_PATH" ]; then
        echo -e "${RED}Error: H2 JAR not found at: $H2_JAR_PATH${NC}"
        echo "Please download H2 or provide the correct path."
        exit 1
    fi
    
    echo -e "${GREEN}Using H2 JAR: $H2_JAR_PATH${NC}"
    echo ""
}

# Function to create backup directory
create_backup_dir() {
    if [ ! -d "$BACKUP_DIR" ]; then
        echo "Creating backup directory: $BACKUP_DIR"
        mkdir -p "$BACKUP_DIR"
    fi
}

# Function to backup database
backup_database() {
    echo -e "${YELLOW}Step 1: Exporting database to SQL script...${NC}"
    
    if [ ! -f "${DB_DIR}/${DB_NAME}.mv.db" ]; then
        echo -e "${RED}Error: Database file not found at: ${DB_DIR}/${DB_NAME}.mv.db${NC}"
        exit 1
    fi
    
    echo "Database file found. Exporting data..."
    echo "URL: $DB_URL"
    echo "Output file: $BACKUP_FILE"
    echo ""
    
    # Export database to SQL script
    java -cp "$H2_JAR_PATH" org.h2.tools.Script \
        -url "$DB_URL" \
        -user "$DB_USER" \
        -password "$DB_PASSWORD" \
        -script "$BACKUP_FILE" \
        -options "DROP,CREATE,SIMPLE"
    
    if [ $? -eq 0 ]; then
        echo -e "${GREEN}✓ Database exported successfully to: $BACKUP_FILE${NC}"
        echo ""
        
        # Show file size
        FILE_SIZE=$(du -h "$BACKUP_FILE" | cut -f1)
        echo "Backup file size: $FILE_SIZE"
        echo ""
    else
        echo -e "${RED}✗ Error exporting database${NC}"
        exit 1
    fi
}

# Function to backup database files
backup_database_files() {
    echo -e "${YELLOW}Step 2: Creating backup of database files...${NC}"
    
    DB_FILES=("${DB_NAME}.mv.db" "${DB_NAME}.trace.db" "${DB_NAME}.lock.db")
    
    for file in "${DB_FILES[@]}"; do
        if [ -f "${DB_DIR}/${file}" ]; then
            BACKUP_FILE_PATH="${BACKUP_DIR}/${file}.${TIMESTAMP}.bak"
            echo "Backing up: $file"
            cp "${DB_DIR}/${file}" "$BACKUP_FILE_PATH"
            echo -e "${GREEN}✓ Backed up to: $BACKUP_FILE_PATH${NC}"
        fi
    done
    echo ""
}

# Function to show restore instructions
show_restore_instructions() {
    echo -e "${GREEN}=== Backup Complete ===${NC}"
    echo ""
    echo "Backup files created:"
    echo "  - SQL Script: $BACKUP_FILE"
    echo "  - Database files: ${BACKUP_DIR}/*.${TIMESTAMP}.bak"
    echo ""
    echo -e "${YELLOW}=== Next Steps ===${NC}"
    echo ""
    echo "1. Delete old database files (optional - for fresh start):"
    echo "   rm ${DB_DIR}/${DB_NAME}.mv.db"
    echo "   rm ${DB_DIR}/${DB_NAME}.trace.db"
    echo "   rm ${DB_DIR}/${DB_NAME}.lock.db"
    echo ""
    echo "2. Start the application - Liquibase will recreate the schema"
    echo ""
    echo "3. To restore data from backup (if needed):"
    echo "   java -cp \"$H2_JAR_PATH\" org.h2.tools.RunScript \\"
    echo "     -url \"$DB_URL\" \\"
    echo "     -user \"$DB_USER\" \\"
    echo "     -password \"$DB_PASSWORD\" \\"
    echo "     -script \"$BACKUP_FILE\" \\"
    echo "     -options \"FROM_1X\""
    echo ""
    echo -e "${GREEN}Script completed successfully!${NC}"
}

# Main execution
main() {
    find_h2_jar
    create_backup_dir
    backup_database
    backup_database_files
    show_restore_instructions
}

# Run main function
main

