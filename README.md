:fire: Thursday, May 12, 2016: due to a lack of free time and interest, this project is over. Feel free to start a fork.

## Java Code Coverage for NetBeans

* The JaCoCoverage Plugin is a NetBeans plugin that enhances the existing NetBeans functionality with new code coverage features.<br>
* The plugin works as a transparent additional service that colors all java files according to the unit tests coverage information. With
code coverage enabled user continues to work with his/her project in the usual way but can easily view the test coverage of the project
classes.<br>The code coverage plugin will update the code coverage data and refresh editors markup every time a unit test (or any selected
Ant target) is executed for the project. Currently the __Java Application__, __Java Library__, __Java Web and Java EE__ (not tested with EAR and EJB projects but may work), Java Project with Existing Sources are supported. __Maven support__ with JaCoCo is already integrated in NetBeans base installation, please check [online how-to](http://wiki.netbeans.org/MavenCodeCoverage) for details.
* Coverage collections are based on JaCoCo in order to **support Java 5, 6, 7 and Java 8 bytecode**. Take it as a modern alternative to the EMMA and
Cobertura based plugins.
* JaCoCo is a free code coverage library for Java, which has been created by the [EclEmma team](http://www.eclemma.org/jacoco/).

TikiOne JaCoCoverage plugin is built with and for the [NetBeans 8.0.1 platform](http://netbeans.org) and the latest version of
[Oracle JDK7](http://www.oracle.com/technetwork/java/javase/downloads/index.html).

### Download stable builds
Stable builds are hosted on the NetBeans [Plugin Portal website](http://plugins.netbeans.org/plugin/48570/tikione-jacocoverage),
validated by the NetBeans Plugin Review staff (I am also a member of this team) and available in the NetBeans integrated **Plugins Manager** (see ``Tools``, ``Plugins``,
``Available Plugins`` and look for the ``TikiOne JaCoCoverage`` plugin).

You can also check the GitHub [releases page](https://github.com/jonathanlermitage/tikione-jacocoverage/releases) for manual installation of NBM files.

### Author
* Jonathan Lermitage (<jonathan.lermitage@gmail.com>)

### Contributor
* Graeme Ingleby - provided NetBeans Module support
