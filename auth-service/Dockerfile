FROM maven:3

ENV APP_HOME=/recipes-app/auth-service

COPY . $APP_HOME/
WORKDIR $APP_HOME

RUN mvn package -DskipTests
ENV JAR_FILE=target/auth-service-*.jar

RUN mv ${JAR_FILE} /auth-service.jar

ENTRYPOINT ["java", "-jar", "/auth-service.jar"]