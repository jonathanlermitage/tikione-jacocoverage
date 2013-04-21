# TikiOne JaCoCoverage - a NetBeans module for JaCoCo code coverage

#### Warning: plugin under development

* The JaCoCoverage Plugin is a Netbeans 7.3 plugin that enhances the existing NetBeans functionality with new code coverage features.<br>
* The plugin works as a transparent additional service that colors all java files according to the unit tests coverage information. With code coverage enabled user continues to work with his/her project in the usual way but can easily view the test coverage of the project classes.<br>The code coverage plugin will update the code coverage data and refresh editors markup every time a unit test (or any selected Ant target) is executed for the project. Currently the Java Application, Java Library and Java Project with Existing Sources are supported. Maven and Gradle support may be added later.
* Coverage collections are based on JaCoCo in order to **support Java 7 bytecode**. Take it as a modern alternative to the EMMA and Cobertura based plugins.

TikiOne JaCoCoverage plugin is built with [NetBeans](http://netbeans.org) and the latest version of [Oracle JDK6](http://www.oracle.com/technetwork/java/javase/downloads/index.html). The latest version of NetBeans will always be used: don't expect support for older NetBeans versions (too much work and nobody uses them).

## Download

* You can get the **latest development build** (for testing purpose only!) at [Sourceforge](https://sourceforge.net/projects/nbjacoco/files/latest_dev_build/).
* After the first stable release, builds will probably be hosted on a Jenkins server provided by [CloudBees](http://www.cloudbees.com). Also, NBM stable files will be hosted on the NetBeans [Plugin Portal website](http://plugins.netbeans.org/PluginPortal/), validated by the NetBeans Plugin Review staff and available in the NetBeans integrated Plugins Manager (see ``Tools``, ``Plugins``, ``Available Plugins``).

## Development progression

####1.0.0:

* ``NetBeans integration (context menu action)``: done.
* ``Configurable``: done.
* ``JaCoCo report generation (binary form and XML transformation)``: done.
* ``JaCoCo report analysis``: done.
* ``Java source files highlighting``: **under development** (implemented but needs stabilization and configuration).

I hope a first stable release between Sunday, April 21 and Friday 26.

####1.1.0:

* ``Detailed HTML report``: todo.
* ``Documentation (JavaHelp integrated into NetBeans)``: todo.

It doesn't need a lot of work. Probably a few days.

## Media

A first screenshot of the development version. JaCoCo coverage is fully functional and code highlighting works (three colors for three coverage states: covered, partially covered and not covered).

![Screenshot](http://netbeanscolors.org/files/jacococoverage_2.png)

## Author
* Jonathan Lermitage (<jonathan.lermitage@entreprise38.org>)

## LICENSE

LGPL License