##### ``>> Project Status >>`` I am currently too much busy: other urgent projects, personal life, a terrible lack of free time and a new job. Ant projects support is over, I prefer to integrate contributions and provide bugfixes only. I'll focus on Maven projects support asap. Thx for your understanding.

## Java Code Coverage for NetBeans

* The JaCoCoverage Plugin is a NetBeans plugin that enhances the existing NetBeans functionality with new code coverage features.<br>
* The plugin works as a transparent additional service that colors all java files according to the unit tests coverage information. With
code coverage enabled user continues to work with his/her project in the usual way but can easily view the test coverage of the project
classes.<br>The code coverage plugin will update the code coverage data and refresh editors markup every time a unit test (or any selected
Ant target) is executed for the project. Currently the Java Application, Java Library, Java Project with Existing Sources and NetBeans
Modules are supported. Maven and Gradle support may be added later.
* Coverage collections are based on JaCoCo in order to **support Java 5, 6, 7 and Java 8 bytecode**. Take it as a modern alternative to the EMMA and
Cobertura based plugins.
* JaCoCo is a free code coverage library for Java, which has been created by the [EclEmma team](http://www.eclemma.org/jacoco/).

TikiOne JaCoCoverage plugin is built with [NetBeans](http://netbeans.org) and the latest version of
[Oracle JDK7](http://www.oracle.com/technetwork/java/javase/downloads/index.html). The latest version of NetBeans will always be used:
don't expect support for older NetBeans versions (too much work and nobody uses them).

### Download stable and dev builds
* Stable builds are hosted on the NetBeans [Plugin Portal website](http://plugins.netbeans.org/plugin/48570/tikione-jacocoverage),
validated by the NetBeans Plugin Review staff and available in the NetBeans integrated **Plugins Manager** (see ``Tools``, ``Plugins``,
``Available Plugins`` and look for the ``TikiOne JaCoCoverage Plugin`` plugin).
* You can get the latest stable development builds by registering the following **Update Center** into NetBeans: ``http://lermitage.biz/nbuc/jacocoverage/updates.xml``. These builds integrate new features and updates that have been tested, there are no unstable nor experimental features inside. Future updates marked as "done" are integrated into the latest development builds, future updates marked as "under development" are not (otherwise they are not activated).

### Development progression
Please see [issues attached to a **milestone**](https://github.com/jonathanlermitage/tikione-jacocoverage/issues/milestones).
For details on previous and current versions, please check the [Changelog](https://github.com/jonathanlermitage/tikione-jacocoverage/blob/master/CHANGELOG.md) file.

### Documentation and Media
[Click Here!](https://github.com/jonathanlermitage/tikione-jacocoverage/blob/master/DOCUMENTATION.md) (work in progress)

### Author
* Jonathan Lermitage (<jonathan.lermitage@gmail.com>)

### License
[WTFPL](http://www.wtfpl.net) License.
