FROM maven:3.5.4-jdk-8

ENV PROJECT_DIR=/opt/project

RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR

ADD ./file-server/pom.xml $PROJECT_DIR
RUN mvn dependency:resolve

ADD ./file-server/src/ $PROJECT_DIR/src
RUN mvn install -DskipTests=true

FROM openjdk:8-jdk
ENV PROJECT_DIR=/opt/project
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR
COPY --from=0 $PROJECT_DIR/target/file-server* $PROJECT_DIR/
EXPOSE 8181 8182 8180 27018 27017
CMD ["java", "-jar", "/opt/project/file-server-1.0.0.jar"]