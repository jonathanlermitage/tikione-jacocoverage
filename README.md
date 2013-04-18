# TikiOne JaCoCoverage - a NetBeans module for JaCoCo code coverage

#### Warning: plugin under development

The JaCoCoverage Plugin is a Netbeans 7.3 plugin that enhances the existing Netbeans functionality with new code coverage features.<br>
The plugin works as a transparent additional service that colors all java files according to the unit tests coverage information.<br>With code coverage enabled user continues to work with his/her project in the usual way but can easily view the test coverage of the project classes.<br>
The code coverage plugin will update the code coverage data and refresh editors markup every time a unit test is executed for the project. Currently the Java Application, Java Library and Java Project with Existing Sources are supported<br>
Coverage collections are based on JaCoCo in order to support Java 7 bytecode.

TikiOne JaCoCoverage plugin is built with NetBeans 7.3 (http://netbeans.org/) and the latest version of Oracle JDK6.

![Screenshot](http://netbeanscolors.org/files/jacococoverage_2.png)

## Development progression
* NetBeans integration: done
* Configurable: done
* JaCoCo report generation (binary form): done
* JaCoCo report analysis: done
* Java source files highlighting: under development
* HTML report: todo

I hope a first release next week.

## Author
* Jonathan Lermitage (<jonathan.lermitage@entreprise38.org>)

## LICENSE

LGPL License