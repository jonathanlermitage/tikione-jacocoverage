## JaCoCoverage Change Log

### Latest Development Version 1.2.0.20130523 (2013-05-23)
* stabilization and optimization: JaCoCoverage task now runs with a RequestProcessor limited to 3 maximum threads.
* fixed a bug: ``jacoco.exec`` file was generated into the project's working directory, not the project's root.
* added support of detailed JaCoCo HTML reports.
* added a warning message when Ant Task or JaCoCo Agent fails.
* updated coverage colors (they are now based on JaCoCo HTML reports).
* removed duplicated JAR files in JaCoCo Library module.
* configuration panel: removed unused options.
* configuration panel: added social icons and a link to the online help.
* some minor UI improvements.

### Version 1.1.1 (2013-05-06)
* integrated Jan Lahoda's [patch](https://github.com/jonathanlermitage/tikione-jacocoverage/pull/3) (preventing NPE when right-clicking on a project that does not have nbproject/project.properties).
* classes displayed in minimal coverage reports are now sorted.
* installing plugins doesn't require to restart NetBeans.

### Version 1.1.0 (2013-04-28)
* added support of NetBeans Modules projects.
* added a "Run Project with JaCoCoverage" action menu and removed the configuration of the Ant task.
* added configuration of code highlighting and reporting (enable/disable).
* added icons on action menus.
* minor enhancement of the minimal coverage report.
* fixed errors when the JaCoCo JavaAgent doesn't generate JaCoCo report file (jacoco.exec).

### Version 1.0.0 (2013-04-22)
* first stable version. Supports Java7 code coverage, code highlighting and minimal coverage report.
* *built with NetBeans 7.3 and Oracle JDK6.*
