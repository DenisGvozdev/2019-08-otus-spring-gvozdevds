FROM maven:3.5.4-jdk-8

ENV PROJECT_DIR=/opt/project

RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR

ADD ./library/pom.xml $PROJECT_DIR
RUN mvn dependency:resolve

ADD ./library/src/ $PROJECT_DIR/src
RUN mvn install -DskipTests=true

FROM openjdk:8-jdk
ENV PROJECT_DIR=/opt/project
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR
COPY --from=0 $PROJECT_DIR/target/library* $PROJECT_DIR/
EXPOSE 8181 27018
CMD ["java", "-jar", "/opt/project/library-1.0.0.jar"]