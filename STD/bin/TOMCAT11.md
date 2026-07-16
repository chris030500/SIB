# Ejecucion local y despliegue en Tomcat 11

## Requisitos
- JDK 17 instalado (`C:\Program Files\Java\jdk-17.0.0.1`).
- Maven Wrapper (`mvnw.cmd`) ya incluido.
- Tomcat 11 instalado.

## Levantar en VS Code
1. Abre la carpeta `STD`.
2. Ejecuta la configuracion de debug `STD - Spring Boot`.
3. O corre la tarea `STD: Maven package (WAR)` para generar el WAR.

## Build por terminal
```powershell
$env:JAVA_HOME='C:\Program Files\Java\jdk-17.0.0.1'
$env:Path="$env:JAVA_HOME\\bin;$env:Path"
./mvnw.cmd "-DskipTests" "-Dmaven.test.skip=true" package
```

## Despliegue en Tomcat 11
1. Toma el archivo `target/STD-0.0.1-SNAPSHOT.war`.
2. Copialo a `TOMCAT_HOME/webapps/`.
3. Inicia Tomcat 11.
4. Accede a la app en `http://localhost:8080/STD-0.0.1-SNAPSHOT`.

Si quieres que el contexto sea `/STD`, renombra el WAR a `STD.war` antes de copiarlo.
