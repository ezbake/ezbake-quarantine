## Quarantine Rest Service

### Running


#### Locally via Maven:

``` mvn clean compile ```

``` mvn jetty:run ```

#### EzCentos via [Jetty Runner]:

``` mvn clean package ```

``` java -jar bin/jetty-runner.jar --port 8181 target/Quarantine.war ```


[Jetty Runner]: http://repo2.maven.org/maven2/org/mortbay/jetty/jetty-runner/
