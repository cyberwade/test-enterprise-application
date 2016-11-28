# test-enterprise-application
To run the test enterprise application you should follow the steps:

1. Create database '**test**' on your PostgreSQL instance.

2. Create database user '**test**' with password '**test**'.

3. Build db-module. Call '**mvn flyway:migrate**' for installing database migration.

4. Build web-module.

5. Deploy the WAR to the JBoss or Tomcat Application Server (both servers of version 7 needed).

6. Open index page, for example by URL *http://localhost:8080/test-enterprise-application/index.xhtml*.
