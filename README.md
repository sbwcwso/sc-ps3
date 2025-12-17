Install the bundled ANTLR JAR into your local Maven repository:

```bash
mvn install:install-file -Dfile=lib/antlr.jar -DgroupId=org.antlr -DartifactId=antlr4-runtime -Dversion=4.5.1 -Dpackaging=jar
```

After modifying `Expression.g4`, regenerate parser sources and compile:

```bash
mvn antlr4:antlr4 compile
```

Run the full test suite:

```bash
mvn test
```

Run the main program:

```bash
mvn exec:java
```