# Testing Guide - Real Database Integration Testing

## Overview
This document describes the comprehensive testing approach implemented for the Pahana Education Management System, using real database integration testing with JUnit 5 annotations.

## Testing Architecture

### 1. Test Structure
- **@BeforeAll**: Setup test data and database connection
- **@AfterAll**: Cleanup test data after all tests
- **@Order**: Ensures tests run in a specific sequence
- **@DisplayName**: Provides descriptive test names
- **@EnabledIf**: Conditionally enables tests based on database availability

### 2. Database Integration
- **Real Database**: Tests use the actual MySQL database
- **Test Data Management**: Automated setup and cleanup of test data
- **Data Isolation**: Each test class has its own test data
- **Clean State**: Database is cleaned before and after test execution

## Test Classes Overview

### 1. TestDatabaseUtil
**Location**: `src/test/java/com/icbt/util/TestDatabaseUtil.java`

**Purpose**: Centralized test database management utility

**Key Features**:
- Database connection verification
- Test data setup and cleanup
- Table-specific cleanup operations
- Test data insertion utilities

**Methods**:
```java
// Check if database is available
static boolean isDatabaseAvailable()

// Setup test data
static void setupTestData()

// Cleanup all test data
static void cleanupTestData()
```

### 2. UserServiceTest
**Location**: `src/test/java/com/icbt/service/UserServiceTest.java`

**Test Coverage**:
- ✅ Input validation (null, empty, whitespace)
- ✅ Valid credentials authentication
- ✅ Invalid credentials handling
- ✅ Database connection errors
- ✅ SQL injection attempts
- ✅ XSS attempts
- ✅ Special characters handling
- ✅ Very long input handling

**Test Order**:
1. Database connection check
2. Empty username validation
3. Null username validation
4. Empty password validation
5. Null password validation
6. Whitespace-only validation
7. Valid credentials (database)
8. Multiple valid users (database)
9. Invalid credentials (database)
10. Wrong password (database)
11. Special characters
12. Very long username
13. SQL injection attempts
14. XSS attempts

### 3. CustomerServiceTest
**Location**: `src/test/java/com/icbt/service/CustomerServiceTest.java`

**Test Coverage**:
- ✅ Customer registration
- ✅ Customer retrieval (all, by ID)
- ✅ Customer updates
- ✅ Customer deletion
- ✅ Edge cases (empty, null, special chars)
- ✅ Very long data handling

**Test Order**:
1. Database connection check
2. Register new customer
3. Get all customers
4. Update existing customer
5. Get customer by account number
6. Get non-existent customer
7. Register with empty name
8. Register with null values
9. Special characters handling
10. Very long data handling

### 4. ItemServiceTest
**Location**: `src/test/java/com/icbt/service/ItemServiceTest.java`

**Test Coverage**:
- ✅ Item registration
- ✅ Item retrieval (all, by ID)
- ✅ Item updates
- ✅ Item deletion
- ✅ Edge cases (empty, null, special chars)
- ✅ Negative values handling

**Test Order**:
1. Database connection check
2. Register new item
3. Get all items
4. Get item by ID
5. Get non-existent item
6. Update existing item
7. Delete item
8. Register with empty name
9. Register with null values
10. Special characters handling
11. Very long data handling
12. Negative price handling

### 5. BillServiceTest
**Location**: `src/test/java/com/icbt/service/BillServiceTest.java`

**Test Coverage**:
- ✅ Bill creation
- ✅ Bill retrieval (all, by ID)
- ✅ Bill updates
- ✅ Bill deletion
- ✅ Edge cases (zero, negative, large amounts)
- ✅ Non-existent references

**Test Order**:
1. Database connection check
2. Add new bill
3. Get all bills
4. Get bill by ID
5. Get non-existent bill
6. Update existing bill
7. Delete bill
8. Zero amount handling
9. Negative amount handling
10. Very large amount handling
11. Non-existent account number
12. Update non-existent bill
13. Delete non-existent bill

### 6. BillItemServiceTest
**Location**: `src/test/java/com/icbt/service/BillItemServiceTest.java`

**Test Coverage**:
- ✅ Bill item creation
- ✅ Bill item retrieval (all, by bill ID)
- ✅ Bill item updates
- ✅ Bill item deletion
- ✅ Edge cases (zero, negative values)
- ✅ Non-existent references

**Test Order**:
1. Database connection check
2. Save bill items
3. Get all bill items
4. Get bill items by bill ID
5. Get non-existent bill items
6. Update bill item
7. Update non-existent bill item
8. Delete bill item
9. Delete non-existent bill item
10. Zero quantity handling
11. Negative quantity handling
12. Zero price handling
13. Negative price handling
14. Very large values handling
15. Non-existent bill ID
16. Non-existent item ID

### 7. DBConnectionTest
**Location**: `src/test/java/com/icbt/util/DBConnectionTest.java`

**Test Coverage**:
- ✅ Database connection validity
- ✅ Connection reuse (singleton pattern)
- ✅ Connection close and reopen
- ✅ Connection properties
- ✅ Transaction handling
- ✅ Concurrent connections
- ✅ Error handling

**Test Order**:
1. Database connection availability check
2. Connection validity
3. Connection reuse
4. Connection close and reopen
5. Connection properties
6. Transaction handling
7. Concurrent connections
8. Error handling

## Test Data Management

### Test Data Setup
Each test class automatically sets up the following test data:

**Users**:
- `testadmin` / `testpass123`
- `testuser1` / `password123`
- `testuser2` / `secret456`

**Customers**:
- John Doe (123 Main St, 0712345678)
- Jane Smith (456 Oak Ave, 0723456789)

**Items**:
- Test Book 1 (Test Description 1, 100.00, 10)
- Test Book 2 (Test Description 2, 200.00, 15)

### Data Cleanup
After each test class completes:
- All test data is automatically cleaned up
- Database is restored to a clean state
- No test data persists between test runs

## Running Tests

### Prerequisites
1. **MySQL Server**: Must be running on localhost:3306
2. **Database**: `pahana-edu` database must exist
3. **Credentials**: Root user with no password (as configured in DBConnection)

### Test Execution
```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=UserServiceTest

# Run tests with verbose output
mvn test -Dtest=UserServiceTest -Dsurefire.useFile=false
```

### Test Output
- **Database Available**: All tests run with real database integration
- **Database Unavailable**: Tests are skipped with informative messages
- **Test Results**: Detailed test results with pass/fail status

## Test Annotations Explained

### @BeforeAll
- **Purpose**: Setup that runs once before all tests in the class
- **Usage**: Initialize services, setup test data, verify database connection
- **Scope**: Static method, runs once per test class

### @AfterAll
- **Purpose**: Cleanup that runs once after all tests in the class
- **Usage**: Clean up test data, close connections, restore database state
- **Scope**: Static method, runs once per test class

### @Order
- **Purpose**: Control the execution order of tests
- **Usage**: Ensure tests run in logical sequence (setup → validation → integration → cleanup)
- **Values**: Integer values, lower numbers run first

### @DisplayName
- **Purpose**: Provide human-readable test names
- **Usage**: Describe what each test is validating
- **Example**: "Test login with valid credentials from database"

### @EnabledIf
- **Purpose**: Conditionally enable tests based on conditions
- **Usage**: Skip tests when database is unavailable
- **Method**: References a static boolean method

## Best Practices

### 1. Test Organization
- **Logical Order**: Tests should follow a logical sequence
- **Dependencies**: Tests that depend on others should run later
- **Cleanup**: Always clean up after tests

### 2. Test Data
- **Isolation**: Each test class has its own test data
- **Cleanup**: Always clean up test data after tests
- **Realistic**: Use realistic test data that matches production

### 3. Error Handling
- **Graceful Degradation**: Tests should handle database unavailability
- **Informative Messages**: Provide clear error messages
- **Conditional Execution**: Skip tests when prerequisites aren't met

### 4. Performance
- **Efficient Setup**: Minimize setup time in @BeforeAll
- **Batch Operations**: Use batch operations for test data setup
- **Connection Reuse**: Reuse database connections when possible

## Troubleshooting

### Common Issues

**1. Database Connection Failed**
```
⚠️  Database not available - skipping integration tests
   Make sure MySQL is running and database 'pahana-edu' exists
```
**Solution**: Start MySQL server and ensure database exists

**2. Test Data Cleanup Failed**
```
Error during test data cleanup: java.sql.SQLException
```
**Solution**: Check database permissions and foreign key constraints

**3. Tests Running Out of Order**
```
Test execution order is not guaranteed
```
**Solution**: Ensure @TestMethodOrder(MethodOrderer.OrderAnnotation.class) is used

### Database Setup
```sql
-- Create database if it doesn't exist
CREATE DATABASE IF NOT EXISTS `pahana-edu`;

-- Import schema
mysql -u root -p pahana-edu < sql/pahana-edu.sql
```

## Future Enhancements

### 1. Test Data Factory
- Create test data factories for more complex scenarios
- Generate realistic test data automatically
- Support for different test scenarios

### 2. Database Migration Testing
- Test database schema changes
- Verify migration scripts
- Test rollback scenarios

### 3. Performance Testing
- Database performance benchmarks
- Connection pool testing
- Query optimization testing

### 4. Integration Testing
- End-to-end workflow testing
- Multi-user scenario testing
- Concurrent access testing

### 5. Test Reporting
- Detailed test reports with database metrics
- Test coverage analysis
- Performance regression detection
