# 📦 Changelog

All significant updates to this project are logged here.  
This project adheres to [Semantic Versioning](https://semver.org/).

---

## [1.0.0] - 2025-08-02 — **Initial Launch**

🚀 **Release Note: Pahana Edu Web v1.0.0**

This marks the first production-ready version of the Pahana Edu web billing system. Built with JSP, Servlets, and Maven, it delivers a full-featured, modular billing platform for the Pahana Edu Bookshop with a polished dark interface and comprehensive service-level test coverage.

---

### ✨ Key Highlights

#### 🔐 User Login System
- User login/logout managed via session
- `LoginServlet` and `LogoutServlet` implemented
- Role-based session validation via `UserService`

#### 👥 Customer Management
- Add, edit, remove, and search customer records
- MVC architecture: controller → service → DAO layers
- Fully unit-tested using JUnit

#### 📦 Item Handling
- Register, update, and delete items
- Item list view styled in HTML tables
- Service and DAO classes tested with `ItemServiceTest`

#### 🧾 Billing Module
- Create bills with real-time total calculation
- Dropdowns for customer and item selection
- Integrated `BillService` for business logic

#### 🧮 Bill Items
- Manage line items in a bill (add/edit/delete)
- Query items by bill ID
- Tested using `BillItemServiceTest`

#### ❓ Help Center
- `help.jsp` gives user guidance
- Linked in the main navigation

#### 📊 Admin Dashboard
- Unified admin interface with cards and quick links
- Responsive layout 

---

### 🧪 Test Coverage

- ✅ Unit tests included for:
    - `UserServiceTest`
    - `CustomerServiceTest`
    - `ItemServiceTest`
    - `BillServiceTest`
    - `BillItemServiceTest`
    - `DBConnectionTest`

- Tests use real database connection with setup/teardown logic

---

### 🔐 Security Enhancements

- JSP files under `webapp` to prevent direct access
- Role-based redirection post-login

---

### 🎨 UI/UX Design

- Consistent buttons, spacing, and form styling
- User-friendly messages 

---

### 📁 Project Organization

/model
/dao
/service
/servlet
/webapp/
/sql


- SQL files:
    - `sql/pahana-edu.sql`
    

---
- Tagged as `v1.0.0` after verification

---
