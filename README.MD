# ## 5-in-a-Row Game

![sample game run](Images/gameSample.gif)

**Overview of game** :
5-in-a-Row, a variation of the famous Connect Four game, is a two-player connection game
in which the players first choose a color and then take turns dropping colored discs from the
top into a nine-column, six-row vertically suspended grid. The pieces fall straight down,
occupying the next available space within the column. The objective of the game is to be the
first to form a horizontal, vertical, or diagonal line of five of one's own discs.

## Dependencies
- Java 8
- To Establish a TCP connection with the server use either
	- Netcat
	- Telnet
	- or similar software
- Docker (Optional)
	- Used to start a Sonarqube instance

# How to Run
use the **Gradle wrapper** in the root directory of the project
	-	**note** verify the gradle wrapper *gradlew* has executable permissions

1: **How to start the Server**

- Option A: (IDE)
--	Import the project into an ide of your choice e.g. IntelIJ,
-   File -> Import from existing sources
-   Import project from external model - Gradle
	 * use auto import
	 * create directories for empty roots
	 * create separate module per source set
	 * use default wrapper
	 * then From the top right hand side, expand the gradle menu (optional if you see some dependency related errors)
		 * perform a refresh dependencies
	- From the project explorer, navigate to src/main/java/server
		- Right click on *Server.java* and select "Run Server.main()"
		- This will start the server on localhost:7080
		-
- Option B: (Command Line)
--	Navigate to the Root directory of the project in the terminal
-   go to src/main/java
-  Compile the Server.java file
	 * run "javac server/Server.java"
- Run the Server
	 * run "java server.Server"
	 * use default wrapper

You should now see the output of "Waiting for players to join game" in either the IDE console or your terminal

2: **Connecting Clients**
- Open up Two new Terminal instances, in both type:
-  Using either telnet or netcat type
	 * a) (netcat) nc localhost 7080
	 * b) (telnet) telnet localhost 7080
- Once both clients are connected you should see output saying that each is connected successfully and the Game has started
**Note**: As the clients main purpose is to display the board and get user input with all of the games state and logic residing on the Server. I did not think it was needed to implement a Client class so decided to use telnet.

3: **Playing the Game**
- In each of the clients, follow along with the prompts given by the server
- Each Client will first be asked to enter a name
- Then each client, starting with the first to connect will be prompted to enter a column number (between 1-9)
- After each player input the new state of the board will be sent to each player
-  A player can quit anytime by typing "quit" as per instructions sent out to each client upon the start of the game
- The Game will continue until either a player has wont (connected 5 disks) or the board is full, which results in a draw

![Game Play Example](/Images/GamePlay.png "Game Play Example")


# Tests
**Note**: I aimed to enable the ability to produce a high standard of test quality and coverage with the use of the below methods rather than actually implementing an acceptable standard of testing for this application e.g. 95% + code coverage

1: **Unit Tests**
- The application contains Unit tests using JUnit to test the functionality of the Game
- To run the tests:
	- navigate to the root directory of the project:
	- type "./gradlew test"
-  Where is the report?
	 * The test report for the Unit tests can be found under the root directory at
	 *  Connect5Game/build/reports/tests/test
	 *  There should be an index.html file here
	 * Open the file in a web browser of your choice
	 * You should see a page like so detailing tests names, status, percentage:

![Unit Test Report Example](/Images/UnitTestReport.png "Unit Test Report Example")


2: **Identify Missing Unit tests**
- Assuming the files in the project can be kept to a naming convention
	- e.g. If a file is named "Connect5Board.java", its corresponding Unit test class should be named Connect5Board.java
-  If this is done, we can define a gradle task to inspect whether each class in a particular directory has a corresponding test class, see the *findMissingTests* task in the *build.gradle* file
- To run the task:
	- Type "./gradlew findMissingTests"
		- if a class has no corresponding tests you should see an output similar to below


![findMissingTests Example](/Images/findMissingTests.png "findMissingTests Example")


3: **Code Quality & Coverage**
- SonarQube [link to sonarqube]([https://www.sonarqube.org/](https://www.sonarqube.org/)) and jacoco [link to jacoco GitHub Repo](https://github.com/jacoco/jacoco) & [jacoco Docs] (https://www.jacoco.org/jacoco/trunk/doc/) were used to measure code quality and code coverage of the unit tests run
- How to launch a SonarQube Instance:
	- N.N Docker is needed

* Navigate to the root directory of the project
* Running SonarQube
	* "./gradlew dockerComposeUp"
	* Note you may need sudo privlidges to run this, if so run "sudo ./gradlew dockerComposeUp"
	This will run SonarQube at 	[localhost:9090]([https://localhost:9090](https://localhost:9090))
	You should see an output like this
![SonarQube startup Example](/Images/SonarQubeStart.png "SonarQube startup Example")
the *0 Projects Analyzed* means that no project has been scanned yet, but this will update once you run a SonarScanner against the project
	- To verify that the docker process is running correctly you can type "docker ps" (this may also need sudo privlidges)
	![Docker processes Example](/Images/DockerProcesses.png "Docker processes Example")

* Using SonarQube and Javacoco to generate code coverage and quality report
	* "./gradlew sonarqube"
	* This runs the SonarScanner against the code in the project as well as running the Javacoco Gradle plugin to generate code coverage statistics. A report is then submitted to the SonarQube server once complete
	![Gradle SonarQube  Example](/Images/GradleSonarQube.png "Gradle SonarQube Example")
- You should now be able to go back to the SonarQube instance and view code coverage statistics and view methods and lines of code that are run in the tests. You should see a report on potential bugs found and also code maintainability
	- The aim of using this was to be able to ensure that quality tests are being produced and measure code coverage and quality. The code coverage percent is calculated and based on this you can set a standard to be met prior to merging in new code. e.g. Code must have 95% code coverage etc

* Stopping SonarQube
	* As docker can be resource intensive on your machine you may want to stop the process running once not needed
	* you can grab the SonarQube instance "CONTAINER ID" from the docker processes list in the terminal if you have it present or else run again "docker ps" and locate it (you may need sudo privlidges)
	* To stop the instance then run "docker stop <your container id here>" e.g. "docker stop 12345be7" (may need sudo privlidges)
	* Note you may need sudo privlidges to run this, if so run "sudo ./gradlew dockerComposeUp"

	![SonarQube Report Example](/Images/SonarQubeReport.png "SonarQube Report Example")
