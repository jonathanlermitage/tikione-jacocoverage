## JaCoCoverage Java7 Code Coverage for NetBeans

* The JaCoCoverage Plugin is a Netbeans 7.3 plugin that enhances the existing NetBeans functionality with new code coverage features.<br>
* The plugin works as a transparent additional service that colors all java files according to the unit tests coverage information. With
code coverage enabled user continues to work with his/her project in the usual way but can easily view the test coverage of the project
classes.<br>The code coverage plugin will update the code coverage data and refresh editors markup every time a unit test (or any selected
Ant target) is executed for the project. Currently the Java Application, Java Library, Java Project with Existing Sources and NetBeans
Modules are supported. Maven and Gradle support may be added later.
* Coverage collections are based on JaCoCo in order to **support Java 7 bytecode**. Take it as a modern alternative to the EMMA and
Cobertura based plugins.
* JaCoCo is a free code coverage library for Java, which has been created by the [EclEmma team](http://www.eclemma.org/jacoco/).

TikiOne JaCoCoverage plugin is built with [NetBeans](http://netbeans.org) and the latest version of
[Oracle JDK6](http://www.oracle.com/technetwork/java/javase/downloads/index.html) (when NetBeans 7.4 will be released, JaCoCoverage will be built with JDK7, since it is the minimal JDK version for NetBeans 7.4). The latest version of NetBeans will always be used:
don't expect support for older NetBeans versions (too much work and nobody uses them).

### Download stable and dev builds
* Stable builds are hosted on the NetBeans [Plugin Portal website](http://plugins.netbeans.org/plugin/48570/tikione-jacocoverage),
validated by the NetBeans Plugin Review staff and available in the NetBeans integrated Plugins Manager (see ``Tools``, ``Plugins``,
``Available Plugins`` and look for the ``TikiOne JaCoCoverage Plugin`` plugin).
* You can get the latest stable development builds by registering the following update center into NetBeans: ``http://jacocoverage.tikione.fr/nbuc/updates.xml``. These builds integrate new features and updates that have been tested, there are no unstable nor experimental features inside. Future updates marked as "done" are integrated into the latest development builds, future updates marked as "under development" are not (otherwise they are not activated).

### Development progression
* ([GitHub#1](https://github.com/jonathanlermitage/tikione-jacocoverage/issues/1)) Multi-line statements coloring: investigating.
* ([GitHub#1](https://github.com/jonathanlermitage/tikione-jacocoverage/issues/1)) Add glyphs in the left margin: under development.
* ([GitHub#1](https://github.com/jonathanlermitage/tikione-jacocoverage/issues/1)) Enable test on single file: under development.
* Improve code highlighting by coloring methods declarations: under development.
* Detailed HTML report: **done**.
* Get coverage data from a remote coverage agent and support various project types (Ant based Java EE, Maven, etc.): under development.

### Documentation
*Work in progress.*

### Media
![Screenshot](http://netbeanscolors.org/files/jacococoverage.png)

### Author
* Jonathan Lermitage (<jonathan.lermitage@gmail.com>)

### License
LGPL License
