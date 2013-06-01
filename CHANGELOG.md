## JaCoCoverage Change Log

### Latest Development Version (2013-06-01)
* improved code highlighting with glyphs in left margin and additional information in lines tooltips.
* stabilization and optimization: JaCoCoverage task now runs with a RequestProcessor limited to 3 maximum threads.
* fixed a bug: jacoco.exec file was generated into the project's working directory, not the project's root.
* added support of detailed JaCoCo HTML reports.
* added a copy of JaCoCo XML report file in project's directory (.jacocoverage/jacoco.latest.xml file).
* added a warning message when Ant Task or JaCoCo Agent fails.
* added a progress bar while JaCoCoverage is loading JaCoCo report and applying code highlighting.
* added color themes for code highlighting.
* updated coverage colors (they are now based on JaCoCo HTML reports).
* removed duplicated JAR files in JaCoCo Library module.
* configuration panel: removed unused options.
* configuration panel: added social icons and a link to the online help.
* added French translation in plugin's description (available for "fr" locales).
* some minor UI improvements.

### Version 1.1.1 (2013-05-06)
* GitHub #2 #3 integrated Jan Lahoda's patch (preventing NPE when right-clicking on a project that does not have nbproject/project.properties).
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
