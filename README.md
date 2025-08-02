# Pahan Edu Web

This is a Java Maven web application built using JSP and Servlets, intended for the automation and application for online billing for Pahan Edu Bookshop

- JSP frontend
- Servlet backend
- WAR packaging using Maven
- Deployment on Apache Tomcat

## 📂 Project Structure
```
pahan-edu-web/
├── pom.xml
├── README.md
└── CHANGELOG.md
└── sql/
├── LICENSE
└── src/
└── main/
├── java/
│ └── com/
│ └── icbt/
│ └── MainServlet.java
└── webapp/
├── test/
├── index.jsp
└── WEB-INF/
└── web.xml
```


## ⚙️ Technologies Used

- Java 21
- Maven
- Jakarta Servlet API
- JSP
- Apache Tomcat (for deployment)
- IntelliJ IDEA

🗃 Database schema is stored under /sql. To set up the database:
mysql -u root -p pahana_edu < sql/pahana-edu.sql


1. Install Java 21 and Apache Tomcat 11.
2. Run mvn clean package to build the WAR file.
3. Copy the target/pahan-edu-web.war to your Tomcat webapps folder.
4. Start Tomcat and visit: http://localhost:8080/pahan_edu_war_exploded/

