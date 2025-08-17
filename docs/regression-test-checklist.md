# 🧪 Regression Testing Checklist for v1.0.0

**Target Branch:** `regression`  
**Merge Source:** `staging`  
**Date:** 2025-08-02
**Tester:** Thakshila Dilrukshi

---

## 🔍 General Goals

- Ensure no existing functionality is broken
- Validate system-wide behavior
- Confirm stable, release-ready build
- Final testing before `main` merge and tagging

---

## ✅ Full System Regression Test


| Module              | Test Case Description                   | Status |
|---------------------|-----------------------------------------|--|
| Login               | Valid & invalid login flow              | ✅ Pass |
| Logout              | Logout clears session properly          | ✅ Pass |
| Customer Management | Full CRUD + UI feedback                 | ✅ Pass |
| Item Management     | Full CRUD + price and category handling | ✅ Pass |
| Billing Module      | Create multiple bills with mixed items  | ✅ Pass |
| Billing Module      | Calculate correct totals                | ✅ Pass |
| Help Page           | Loads with instructions for each module | ✅ Pass |
| Navigation          | Links work across dashboard and sidebar | ✅ Pass |
| UI Theme            | Dark theme applied globally             | ✅ Pass |


---

## 🧪 JUnit Testing

| Task                                          | Status |
|-----------------------------------------------|-|
| All unit tests pass (`mvn test`)              | ✅ Pass |
| No skipped or ignored test cases              | ✅ Pass|
| Test logs clean (no unexpected output/errors) | ✅ Pass|


---

## 🛡 Security & Routing

| Test Case                                            | Status |
|------------------------------------------------------|--|
| Protected views are not accessible without login     | ✅ Pass |
| URLs under `WEB-INF/views` cannot be opened directly | ✅ Pass |
| Session expiration handled gracefully                | ✅ Pass |


---

## 🧼 Release Readiness


| Task                                         | Status |
|----------------------------------------------|-|
| `pom.xml` version is `1.0.0`                 |✅ Confirmed |
| `CHANGELOG.md` matches implemented modules   |✅ Confirmed|
| UI content free of typos or placeholder text |✅ Confirmed |
| Application is stable on final deploy test   | ✅ Confirmed |

---


### 🚀 Ready to Merge into `main`: ✅Yes
