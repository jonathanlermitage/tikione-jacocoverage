# TikiOne JaCoCoverage - Changelog

## 1.1.1 2013-05-06

* integrated Jan Lahoda's [patch](https://github.com/jonathanlermitage/tikione-jacocoverage/pull/3) (preventing NPE when right-clicking on a project that does not have nbproject/project.properties).
* classes displayed in minimal coverage reports are now sorted.
* installing plugins doesn't require to restart NetBeans.

## 1.1.0 2013-04-28

* added support of NetBeans Modules projects.
* added a "Run Project with JaCoCoverage" action menu and removed the configuration of the Ant task.
* added configuration of code highlighting and reporting (enable/disable).
* added icons on action menus.
* minor enhancement of the minimal coverage report.
* fixed errors when the JaCoCo JavaAgent doesn't generate JaCoCo report file (jacoco.exec).

## 1.0.0 2013-04-22

* first stable version. Supports Java7 code coverage, code highlighting and minimal coverage report.
* built with NetBeans 7.3 and Oracle JDK6.
