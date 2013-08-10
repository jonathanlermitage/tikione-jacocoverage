## JaCoCoverage Change Log

### Latest DevBuild (Version 1.3.0 scheduled for NetBeans 7.4, which runs on JDK7 only)
* fixed: unclosed input stream on project's properties.
* *built with NetBeans 7.3.1 and Oracle JDK7.*

### Version 1.2.9.20130804.1649 (2013-08-04)
* GitHub #11 added configuration at project level.
* GitHub #14 fixed MissingResourceException when assertions (-ea switch) are enabled.
* disabled NetBeans Module projects support (will be fixed later).
* coloration of multi-line instructions is now enabled in default configuration.
* removed actions from NetBeans toolbar.
* removed actions icons.
* internal: library modules are now hidden in Plugins Manager.

### Version 1.2.2.20130617.1157 (2013-06-17)
* GitHub #9 fixed JavaAgent didn't generate report if its path contained comma.
* minor UI fixes in options panel (relevant on systems with large fonts, like Ubuntu).
* added coloration of multi-line instructions (can be enabled in options panel).

### Version 1.2.1.20130612.1950 (2013-06-12)
* fixed: hide action menu items when disabled.
* added an option to keep JaCoCo binary and XML report files, in their original form or compressed with Zip format.
* updated JaCoCo library to 0.6.3.
* removed Lombok 0.11.8 lib since it is not compatible with JDK8 compiler.
* removed registration of JaCoCo Ant Library into NetBeans.
* disabled JaCoCoverage action items when multiple projects are selected.
* *built with NetBeans 7.3.1 and Oracle JDK6.*

### Version 1.2.0.20130602.325 (2013-06-02)
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
