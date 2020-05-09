################################################
# JAVA MICROSERVICE IMAGE #
################################################
FROM maven:3.5.4-jdk-8
ENV PROJECT_DIR=/usr/src/app

RUN mkdir -p $PROJECT_DIR
#RUN mvn dependency:resolve

ADD ./src/ $PROJECT_DIR/src
COPY ./pom.xml $PROJECT_DIR

WORKDIR $PROJECT_DIR

RUN mvn install

#------------
FROM maven:3.5.4-jdk-8
ENV PROJECT_DIR=/usr/src/app

RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR
COPY --from=0 $PROJECT_DIR/target/adapter18* $PROJECT_DIR/

#------------
EXPOSE 8080

CMD ["java", "-jar", "/usr/src/app/adapter18-1.0.1.jar"]
