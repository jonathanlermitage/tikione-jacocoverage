# TikiOne JaCoCoverage - Java7 Code Coverage for NetBeans

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
[Oracle JDK6](http://www.oracle.com/technetwork/java/javase/downloads/index.html). The latest version of NetBeans will always be used:
don't expect support for older NetBeans versions (too much work and nobody uses them).

## Download

Current stable version is 1.1.0.

* Stable builds are hosted on the NetBeans [Plugin Portal website](http://plugins.netbeans.org/plugin/48570/tikione-jacocoverage),
validated by the NetBeans Plugin Review staff and available in the NetBeans integrated Plugins Manager (see ``Tools``, ``Plugins``,
``Available Plugins`` and look for ``TikiOne`` plugins).
* You can get the latest development build at [Sourceforge.net](https://sourceforge.net/projects/nbjacoco/files/latest_dev_build/).
Future updates marked as "done" are integrated into the latest development builds.

## Development progression

* ``Multi-line statements coloring`` [GitHub#1](https://github.com/jonathanlermitage/tikione-jacocoverage/issues/1): **investigating**.
* ``Add glyphs in the left margin`` [GitHub#1](https://github.com/jonathanlermitage/tikione-jacocoverage/issues/1): **under development
and scheduled for 1.2.0**.
* ``Enable test on single file`` [GitHub#1](https://github.com/jonathanlermitage/tikione-jacocoverage/issues/1): **under development**.
* ``Unlock configuration of highlighting colors``: todo (help needed!).
* ``Detailed HTML report``: todo.
* ``Documentation (JavaHelp integrated into NetBeans)``: todo.

## Media

![Screenshot](http://netbeanscolors.org/files/jacococoverage.png)

## Author
* Jonathan Lermitage (<jonathan.lermitage@entreprise38.org>)

You are an IT or industrial company which has an office in France? I'm open to hiring: I will integrate an engineering school (Computer
Science and Mathematics) in September, and I look for a three-year contract (part-time at school, part-time work).

## License

LGPL License
