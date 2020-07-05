# Demo Recipe App

#  Features

  - Fully implementation of CRUD operations of both Recipe and Ingredients
  - Fully Implementation of REST Services using JAX-RS web services
  - All jUnit test cases are written and working as expected
  - A single page application is created using servlet (only listing and creating of recipes).
  - A single Generic implementation of CRUD operation using FACADE design pattern which shows knowledge of EJB.
  - Only container managed transactions are used to maintain ACIDty of the transactions.


#  Prerequisites
  - Java jdk 8.0 
  - NetBeans IDE 8.2
  - Glassfish Server 5.0.1
  - Derby Database embeded with glassfish server
  - for persistence **jdbc/sample** jdbc resource must be configured in the glassfish server it is configured by default but in case it is not present this needs to be created otherwise things will fail.
  - In **pom.xml** file the location of **glassfish-embedded-static-shell.jar** file must be specified otherwise junit will not work the same can be found in root directory of glassfish and **glassfish/lib/embedded**

#  Limitations
   - junit test cases highly dependent on the test data so the test data needs to be properly setup due to which test cases it self create the data first and then executes.
   - test cases for REST services are written but not working at the moment since some version conflicts are still there and needs more investigation.
   - Glassfish instance must be running in order successful execution of test cases.

#  Screen prints
##  Successful build of the project
<img src="proofs/project build success with all tests.JPG" style="float: left; margin-right: 10px;" />
