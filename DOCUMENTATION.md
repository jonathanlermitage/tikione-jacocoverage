## JaCoCoverage Documentation

### What Is JaCoCoverage
The JaCoCoverage Plugin is a Netbeans plugin that enhances the existing NetBeans functionality with new code coverage features. In other words, you can launch your program or test it and JaCoCoverage will provide you coverage information by highlighting your source code files and providing various types of reports (currently, textual and HTML). This way, you can easily know what piece of code has been executed or not.

### How It Works
The JaCoCoverage Plugin is based on JaCoCo: a library created by the [EclEmma team](http://www.eclemma.org/jacoco/). This library is able to generate coverage data when you launch a Java program.
More precsisely, JaCoCoverage uses the JaCoCo Java Agent to enable coverage collections (i.e. a JaCoCo Java Agent is attached to your program).
When your program terminates, JaCoCo generates coverage data and JaCoCoverage assures the coverage information integration in your NetBeans IDE.
JaCoCo and JaCoCoverage support Java 7 and 8 and are also tested with Java 6. It may work with Java 5 too.

### Media
NetBeans Java editor showing highlighted code. You can see covered, partially covered and not covered code, and annotations in the left margin:

![Screenshot](http://netbeanscolors.org/files/jacococoverage_editor.png)

A context menu is enabled for supported projects. It allows you to run and test your programs with JaCoCoverage:

![Screenshot](http://netbeanscolors.org/files/jacococoverage_menu.png)

JaCoCoverage is configurable:

![Screenshot](http://netbeanscolors.org/files/jacococoverage_options.png)

A short textual coverage report:

![Screenshot](http://netbeanscolors.org/files/jacococoverage_consolereport.png)

A complete HTML coverage report:

![Screenshot](http://netbeanscolors.org/files/jacococoverage_htmlreport.png)

### Author
* Jonathan Lermitage (<jonathan.lermitage@gmail.com>)

### License
[WTFPL](http://www.wtfpl.net) License. In other words, you can do what you want: this project is entirely OpenSource, Free and Gratis.
