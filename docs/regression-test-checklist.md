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

| Module              | Test Case Description                   | Status          |
|---------------------|-----------------------------------------|-----------------|
| Login               | Valid & invalid login flow              | ☐ Pass / ☐ Fail |
| Logout              | Logout clears session properly          | ☐ Pass / ☐ Fail |
| Customer Management | Full CRUD + UI feedback                 | ☐ Pass / ☐ Fail |
| Item Management     | Full CRUD + price and category handling | ☐ Pass / ☐ Fail |
| Billing Module      | Create multiple bills with mixed items  | ☐ Pass / ☐ Fail |
| Billing Module      | Calculate correct totals                | ☐ Pass / ☐ Fail |
| Help Page           | Loads with instructions for each module | ☐ Pass / ☐ Fail |
| Navigation          | Links work across dashboard and sidebar | ☐ Pass / ☐ Fail |
| UI Theme            | Dark theme applied globally             | ☐ Pass / ☐ Fail |

---

## 🧪 JUnit Testing

| Task                                          | Status          |
|-----------------------------------------------|-----------------|
| All unit tests pass (`mvn test`)              | ☐ Pass / ☐ Fail |
| No skipped or ignored test cases              | ☐ Pass / ☐ Fail |
| Test logs clean (no unexpected output/errors) | ☐ Pass / ☐ Fail |

---

## 🛡 Security & Routing

| Test Case                                            | Status          |
|------------------------------------------------------|-----------------|
| Protected views are not accessible without login     | ☐ Pass / ☐ Fail |
| URLs under `WEB-INF/views` cannot be opened directly | ☐ Pass / ☐ Fail |
| Session expiration handled gracefully                | ☐ Pass / ☐ Fail |

---

## 🧼 Release Readiness

| Task                                         | Status      |
|----------------------------------------------|-------------|
| `pom.xml` version is `1.0.0`                 | ☐ Confirmed |
| `CHANGELOG.md` matches implemented modules   | ☐ Confirmed |
| UI content free of typos or placeholder text | ☐ Confirmed |
| Application is stable on final deploy test   | ☐ Confirmed |

---

## 🗒 Notes

- _[List issues, bugs, or concerns]_

---

### 🚀 Ready to Merge into `main`: ☐ Yes / ☐ No