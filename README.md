# JasperReports Shadow Jar

This project was created to show to my students the use of JasperReports Inside a Uber/Shadow Jar.

It is a JavaFx project where you can press a button, see the progress on a progress bar, and at the end a pdf file will be opened automatically, a pdf wile will be created at user.home/Downloads ( so write permission will be needed ).

Two services was created one to load data and other to generate the report.

This two services show their process on the same progress bar.

It uses maven to automate project execution and distribution.

The project uses custom jasperreports properties to configure custom fonts to be used in reports and distributed with uber JarApp. 

# Running

```bash
mvn clean javafx:run
```

After clone you can use any maven IDE ( or editor ), to access project folder and type `mvn clean javafx:run`, all dependencies will be handled by gradle, and the project window will be opened.

# Contributing

Please contact-me if you need some help or just want to contribute with something.

# This project uses

- Maven
- Maven Shade Plugin
- Java 22
- Lombok  
- JavaFX
- JasperReports
- Log4j2, configured with yml file

# Roadmap

- [x] Compile reports and remove .jrxml from jar

# Licence

This project is licensed under [MIT License](https://choosealicense.com/licenses/mit/).

# Changelog

2018-04-25 - Changed gradle build script to compile and remove jrxml files from generated jar.
2018-04-12 - Added a LocalDate field to report, to show how to use date formats on jasperreports.
2024-07-20 - Removed gradle and added maven to project, upgraded to java 22.

# Maven notes

```bash
# To execute maven in debug mode
mvn -X clean package
```
