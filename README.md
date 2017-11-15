# JasperReports Shadow Jar

This project was created to show to my students the use of JasperReports Inside a Uber/Shadow Jar.

It is a JavaFx project where you can press a button, see the progress on a progress bar, and at the end a pdf file will be opened automatically, a pdf wile will be created at user.home/Downloads ( so write permission will be needed ).

Two services was created one to load data and other to generate the report.

This two services show their process on the same progress bar. 

I used gradle to automate project execution and distribution.

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

# Licence

This projet is licenced under [MIT Licence](https://choosealicense.com/licenses/mit/).
