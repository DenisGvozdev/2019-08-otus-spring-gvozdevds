FROM maven:3.5.4-jdk-8
ENV PROJECT_DIR=/usr/src/app
RUN mkdir -p $PROJECT_DIR
ADD ./src/ $PROJECT_DIR/src
COPY ./pom.xml $PROJECT_DIR
WORKDIR $PROJECT_DIR
RUN mvn clean install -DskipTests=true

FROM openjdk:8-jdk
ENV PROJECT_DIR=/usr/src/app
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR
COPY --from=0 $PROJECT_DIR/target/file-server* $PROJECT_DIR/
EXPOSE 8181 27018
CMD ["java", "-jar", "/usr/src/app/file-server-1.0.0.jar"]