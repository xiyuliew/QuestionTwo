# QuestionTwo

Guide to run this program :- 
1. Clone the project into local device
2. Open the project with Eclipse IDE
3. Wait for eclipse to download the dependencies
4. Run src/main/java/com/example/QuestionTwo/QuestionTwoApplication.java as Java application and the program will run!
5.  http://localhost:8080/h2-console/login.do is the local h2 database with the following credentials :- 
    - Driver Class : org.h2.Driver
    - JDBC URL: jdbc:h2:mem:testdb
    - username : admin
    - password : password
6. http://localhost:8080/api/initializeJob will start the schedule the CronJob to calculate total gainers and losers, then insert into h2 databases.
7 
