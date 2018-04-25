# JasperReports Shadow Jar

This project was created to show to my students the use of JasperReports Inside a Uber/Shadow Jar.

It is a JavaFx project where you can press a button, see the progress on a progress bar, and at the end a pdf file will be opened automatically, a pdf wile will be created at user.home/Downloads ( so write permission will be needed ).

Two services was created one to load data and other to generate the report.

This two services show their process on the same progress bar.

I used gradle to automate project execution and distribution.

The project uses custom jasperreports properties to configure custom fonts to be used in reports and distributed with uber JarApp. 

# Running

I'm not versioning gradle binary, so you will need a local gradle installed, then to use a correct gradle version, you can run `gradle wraper`, after that use use `.gradlew` to execute gradle tasks.

After clone you can use eclipse, intellij or any other IDE ( or editor ), to access project folder and type `./gradlew run`, all dependencies will be handled by gradle, and the project window will be opened.

# Contributing

Please contact-me if you need some help or just want to contribute with something.

# This project uses

- Gradle
- [Gradle Shaddow plugin](https://github.com/johnrengelman/shadow)
- Java >= 8
- Lombok  
- JavaFX
- JasperReports
- Log4j2, configured with yml file

# Roadmap

- Compile reports and remove .jrxml from jar (DONE)

# Licence

This project is licensed under [MIT License](https://choosealicense.com/licenses/mit/).

# Changelog

2018-04-25 - Changed gradle build script to compile and remove jrxml files from generated jar.
2018-04-12 - Added a LocalDate field to report, to show how to use date formats on jasperreports.

