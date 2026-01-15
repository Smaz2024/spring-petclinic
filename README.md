spring-petclinic: PetClinic Example using Spring 4.x  
======================================================
Author: Ken Krebs, Juergen Hoeller, Rob Harrop, Costin Leau, Sam Brannen, Scott Andrews  
Level: Advanced  
Technologies: JPA, Junit, JMX, Spring MVC Annotations, AOP, Spring Data, JSP, webjars, Dandellion  
Summary: The `spring-petclinic` quickstart shows how to run the Spring PetClinic Application in JBoss EAP using the JBoss EAP BOMs.  
Target Product: JBoss EAP  
Source: <https://github.com/jboss-developer/jboss-eap-quickstarts/>  

What is it?  
-----------
The `spring-petclinic` quickstart shows how to run the [Spring PetClinic](<http://github.com/spring-projects/spring-petclinic>) Application 
in Red Hat JBoss Enterprise Application Platform with the use of Red Hat JBoss EAP BOMs (_for the best compatibility_). One of the major 
changes is the use of the `webapp/WEB-INF/jboss-deployment-structure.xml` file. This file specifies which modules 
to include or exclude when building the application. In this case, we exclude Hibernate libraries since the application 
uses Spring Data JPA. Additionally, this is only required when using the spring-data-jpa profile, see `resources/spring/business-config.xml`.

For detailed explanation of the changes made to adapt the Quickstart to Red Hat JBoss Enterprise Application Platform see: [CHANGES.md](CHANGES.md)

PetClinic features alternative DAO implementations and application configurations for JDBC, JPA, and Spring Data JPA, with 
HSQLDB and MySQL as target databases. The default PetClinic configuration is JPA on HSQLDB.  

* The `src/main/resources/spring/business-config.xml` pulls in `src/main/resources/spring/data-access.properties` to set 
the JDBC-related settings for the JPA EntityManager definition. 
    * A simple comment change in `data-access.properties` switches between the data access strategies. 
* In `webapp/WEB_INF/web.xml` the `<param-name>spring.profiles.active</param-name>` using `<param-value>jpa</param-value>` 
(_as the default_) refers to the bean to be used in `src/main/resources/spring/business-config.xml`. 
    * Setting the `<param-value>` to `jdbc`, `jpa`, or `spring-data-jpa` is all that is needed to change the DAO implementation.

All versions of PetClinic also demonstrate JMX support via the use of `<context:mbean-export/>` in `resources/spring/tools-config.xml` 
for exporting MBeans. The `CallMonitoringAspect.java` is exposed using Spring's `@ManagedResource` and `@ManagedOperation`
annotations and with `@Around` annotation we add monitoring around all `org.springframework.stereotype.Repository *` functions. 
You can start up the JDK's JConsole to manage the exported bean.

The use of `@Cacheable` is also demonstrated in `ClinicServiceImpl.java` by caching the results of the method `findVets`.
The cacheManager in configured in `tools-config.xml` and `ehcache.xml` specifies the `vets` cache properties.

The default transaction manager for JDBC is DataSourceTransactionManager and for JPA and Spring Data JPA, JpaTransactionManager.
Those local strategies allow for working with any locally defined DataSource. These are defined in the `business-config.xml`

_Note that the sample configurations for JDBC, JPA, and Spring Data JPA configure a DataSource from the Apachce Tomcat JDBC Pool project for connection pooling. See `datasource-config.xml`._

System requirements  
-------------------

The application this project produces is designed to be run on Red Hat JBoss Enterprise Application Platform 7 or later. 

All you need to build this project is Java 8.0 (Java SDK 1.8) or later and Maven 3.1.1 or later. See [Configure Maven for JBoss EAP 7](https://github.com/jboss-developer/jboss-developer-shared-resources/blob/master/guides/CONFIGURE_MAVEN_JBOSS_EAP7.md#configure-maven-to-build-and-deploy-the-quickstarts) to make sure you are configured correctly for testing the quickstarts.


Start the JBoss EAP Server  
-------------------------

1. Open a command line and navigate to the root of the JBoss EAP directory.
2. The following shows the command line to start the server with the default profile:

        For Linux:   EAP7_HOME/bin/standalone.sh
        For Windows: EAP7_HOME\bin\standalone.bat


Build and Deploy the Quickstart
-------------------------

1. Make sure you have started the JBoss EAP server as described above.
2. Open a command line and navigate to the root directory of this quickstart.
3. Type this command to build and deploy the archive:

        mvn clean package wildfly:deploy -DskipTests


4. This will deploy `spring-petclinic/target/jboss-spring-petclinic.war` to the running instance of the server.

If you don't have maven configured you can manually copy `spring-petclinic/target/jboss-spring-petclinic.war` to EAP7_HOME/standalone/deployments.

For MySQL, you'll need to use the corresponding schema and SQL scripts in the "db/mysql" subdirectory.

In you intend to use a local DataSource, the JDBC settings can be adapted in "src/main/resources/spring/datasource-config.xml". 
To use a JTA DataSource, you need to set up corresponding DataSources in your Java EE container.

 
Access the application
---------------------

The application will be running at the following URL: <http://localhost:8080/jboss-spring-petclinic/>.

_Note:_ You see the following warning in the server log when you access the application. This example does not provide a `dandelion.properties` file because it does not require any changes to the dandelion default configuration. You can ignore this warning.

    WARN  [com.github.dandelion.core.config.StandardConfigurationLoader] (default task-1) No file "dandelion.properties" was found in "dandelion/dandelion.properties" (classpath). The default configuration will be used.


Undeploy the Archive
--------------------

1. Make sure you have started the JBoss EAP server as described above.
2. Open a command line and navigate to the root directory of this quickstart.
3. When you are finished testing, type this command to undeploy the archive:

        mvn wildfly:undeploy


Run the Arquillian Functional Tests
-----------------------------------

This quickstart provides Arquillian functional tests as well. They are located in the `functional-tests/` subdirectory under 
the root directory of this quickstart. Functional tests verify that your application behaves correctly from the user's point 
of view. The tests open a browser instance, simulate clicking around the page as a normal user would do, and then close the browser instance.

NOTE: The arquillian-based functional tests deploy the application, so be sure you have undeployed it before you begin. To run these tests, you must build the main project as described above.

1. Open a command line and navigate to the root directory of this quickstart.
2. If the application is still deployed from the previous section, undeploy it now.

        mvn wildfly:undeploy
3. Build the quickstart WAR using the following command:

        mvn clean package

4. Navigate to the functional-tests/ directory in this quickstart.
5. If you have a running instance of the JBoss EAP server, as described above, run the remote tests by typing the following command:

        mvn clean verify -Parq-wildfly-remote

6. If you prefer to run the functional tests using managed instance of the JBoss EAP server, meaning the tests will start the server for you, type the following command:

        mvn clean verify -Parq-wildfly-managed

7. The `spring-petclinic` quickstart contains three configurations: JDBC, JPA, and Spring Data JPA. You should see the tests run 3 times, one for each configuration. 

8. Review the server log. You will see an exception for each test configuration run similar to the following in the server log.  This is intentional to demonstrate how exceptions are handled within application. This the same exception you can test by clicking on the `Error` menu item in the upper right corner in the deployed application. The application shows a nice error page in the browser instead of the exception. 

        WARN  [warn] (default task-15) Handler execution resulted in exception: java.lang.RuntimeException: Expected: controller used to showcase what happens when an exception is thrown
	        at org.springframework.samples.petclinic.web.CrashController.triggerException(CrashController.java:35)
	        at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	        at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	        at java.lang.reflect.Method.invoke(Method.java:497)
	        at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:221)
          (remainder of StackTrace removed for readability)


Run the Quickstart in Red Hat JBoss Developer Studio or Eclipse
-------------------------------------
You can also start the server and deploy the quickstarts or run the Arquillian tests from Eclipse using JBoss tools. For general information about how to import a quickstart, add a JBoss EAP server, and build and deploy a quickstart, see [Use JBoss Developer Studio or Eclipse to Run the Quickstarts](https://github.com/jboss-developer/jboss-developer-shared-resources/blob/master/guides/USE_JBDS.md#use-jboss-developer-studio-or-eclipse-to-run-the-quickstarts) 



Debug the Application
----------------------

Note: Eclipse/JBDS may generate a persistence.xml file in the src/main/resources/META-INF/ directory. In order to avoid 
errors delete this file.

If you want to debug the source code or look at the Javadocs of any library in the project, run either of the following 
commands to pull them into your local repository. The IDE should then detect them.

        mvn dependency:sources
        mvn dependency:resolve -Dclassifier=javadoc

---

## Project Changes & Migration Notes

**Summary of POM Changes:**
- Removed Cobertura plugin 
- Updated Maven WAR plugin to 3.3.2 for modern Java support.
- Removed deprecated/missing JBoss BOMs.
- Added JAXB dependencies
- Added cglib and asm for Spring test proxy support.

**Why:**
- Ensures compatibility with current Java and Maven versions.
- Avoids build failures due to unsupported plugins/dependencies.
- Maintains test and runtime stability.

---

## Deploying to JBoss EAP 7

1. **Build the WAR:**
   ```
   mvn clean package
   ```
2. **Locate the WAR file:**
   - Find `jboss-spring-petclinic.war` in the `target/` directory.

3. **Deploy to JBoss EAP 7:**
   - Copy the WAR to the JBoss EAP 7 `standalone/deployments/` folder:
     ```
     cp target/jboss-spring-petclinic.war $JBOSS_HOME/standalone/deployments/
     ```
   - Or use the WildFly Maven plugin:
     ```
     mvn wildfly:deploy
     ```

4. **Start JBoss EAP:**
   ```
   $JBOSS_HOME/bin/standalone.sh
   ```

5. **Access the application:**
   - Open: `http://localhost:8080/jboss-spring-petclinic/`

---

## Health Check Endpoints

The application provides two health check endpoints:

- **`GET /jboss-spring-petclinic/health`** - Returns JSON with application status (200 on success, 500 on failure)
- **`GET /jboss-spring-petclinic/healthz`** - Returns plain text "OK" (200 on success, 500 on failure)

Both endpoints verify database connectivity and can be used for monitoring.

---

## Troubleshooting Deployment Issues

### MySQL Configuration Issues

**Problem:** Application deploys but returns 404 or fails to start when configured for MySQL.

**Solution:** To use MySQL instead of the default HSQLDB:

1. **Edit `src/main/resources/spring/data-access.properties`:**
   - Uncomment MySQL settings:
   ```properties
   jdbc.driverClassName=com.mysql.jdbc.Driver
   jdbc.url=jdbc:mysql://localhost:3306/petclinic
   jdbc.username=petclinic
   jdbc.password=petclinic
   jpa.database=MYSQL
   ```
   - Comment out HSQLDB settings

2. **Edit `src/main/resources/spring/datasource-config.xml`:**
   - If MySQL database already has tables and data, **disable automatic database initialization**:
   ```xml
   <!-- Disabled for MySQL: Database already initialized -->
   <!--
   <jdbc:initialize-database data-source="dataSource">
       <jdbc:script location="${jdbc.initLocation}"/>
       <jdbc:script location="${jdbc.dataLocation}"/>
   </jdbc:initialize-database>
   -->
   ```
   - Keep this enabled only if you need to auto-initialize the database

3. **Verify MySQL database:**
   ```sql
   mysql -h localhost -u petclinic -p
   USE petclinic;
   SHOW TABLES;
   ```
   - Should show: owners, pets, specialties, types, vets, vet_specialties, visits

4. **Deploy:**
   ```
   mvn package wildfly:deploy -DskipTests
   ```

---

### Metaspace OutOfMemoryError

**Problem:** `java.lang.OutOfMemoryError: Metaspace` during deployment.

**Solution:** Increase MaxMetaspaceSize in EAP startup script:

**Windows:** Edit `%JBOSS_HOME%\bin\standalone.conf.bat`
```batch
set "JAVA_OPTS=-Xms1G -Xmx1G -XX:MetaspaceSize=96M -XX:MaxMetaspaceSize=512m"
```

**Linux:** Edit `$JBOSS_HOME/bin/standalone.conf`
```bash
JAVA_OPTS="-Xms1G -Xmx1G -XX:MetaspaceSize=96M -XX:MaxMetaspaceSize=512m"
```

Then restart EAP 7.

---

### Duplicate Plugin Declaration Error

**Problem:** Maven error: `'build.plugins.plugin.(groupId:artifactId)' must be unique but found duplicate declaration`

**Solution:** Ensure only one `maven-surefire-plugin` declaration exists in `pom.xml` around line 390.

---

### Version Compatibility Issues

**Problem:** Deployment fails with `java.lang.UnsupportedClassVersionError` or class file version errors.

**Supported Configurations:**
- ✅ **JBoss EAP 7.x** + **Java 8** (Recommended)
- ✅ **WildFly 26+** + **Java 11+**
- ❌ **WildFly 30+** + **Java 8** (Not supported - WildFly 30 requires Java 11+)

Ensure you're using **EAP 7 with Java 8** for this quickstart.

---

### Quick Deployment Checklist

- [ ] JBoss EAP 7 installed and configured
- [ ] Java 8 (JDK 1.8) installed
- [ ] Maven 3.1.1 or later installed
- [ ] Database configured (HSQLDB or MySQL)
- [ ] MaxMetaspaceSize ≥ 512MB (if using MySQL with large WAR)
- [ ] No duplicate surefire plugins in pom.xml
- [ ] EAP 7 started: `standalone.bat` or `standalone.sh`
- [ ] Run: `mvn package wildfly:deploy -DskipTests`
- [ ] Access: `http://localhost:8080/jboss-spring-petclinic/`

If deployment still fails, check `$JBOSS_HOME/standalone/log/server.log` for detailed error messages.
