FROM maven:3

ENV APP_HOME=/recipes-app/user-service

COPY . $APP_HOME/
WORKDIR $APP_HOME

RUN mvn package -DskipTests
ENV JAR_FILE=target/user-service-*.jar

RUN mv ${JAR_FILE} /user-service.jar

ENTRYPOINT ["java", "-jar", "/user-service.jar"]