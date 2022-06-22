FROM maven:3

ENV APP_HOME=/recipes-app/recipe-service

COPY . $APP_HOME/
WORKDIR $APP_HOME

RUN mvn package -DskipTests
ENV JAR_FILE=target/recipe-service-*.jar

RUN mv ${JAR_FILE} /recipe-service.jar

ENTRYPOINT ["java", "-jar", "/recipe-service.jar"]