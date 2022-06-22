FROM maven:3

ENV APP_HOME=/recipes-app/eureka-server

COPY . $APP_HOME/
WORKDIR $APP_HOME

RUN mvn package -DskipTests
ENV JAR_FILE=target/eureka-server-*.jar

RUN mv ${JAR_FILE} /eureka-server.jar

ENTRYPOINT ["java", "-jar", "/eureka-server.jar"]