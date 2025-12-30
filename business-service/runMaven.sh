#!/bin/bash
# Author: Rohtash Lakra
# Maven run script for Spring Boot application
# 
# This script runs the Maven/Spring Boot application.
#
# Source common version function
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
source "${SCRIPT_DIR}/version.sh"

clear
echo
echo "${JAVA_HOME}"
echo

# Run Spring Boot application
SNAPSHOT_VERSION=$(buildVersion SNAPSHOT)
RELEASE_VERSION=$(buildVersion)
mvn clean spring-boot:run -Drevision=$RELEASE_VERSION
echo
