FROM maven:3.5.4-jdk-8

ENV PROJECT_DIR=/opt/project

RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR

ADD ./eureka/pom.xml $PROJECT_DIR
RUN mvn dependency:resolve

ADD ./eureka/src/ $PROJECT_DIR/src
RUN mvn install -DskipTests=true

FROM openjdk:8-jdk
ENV PROJECT_DIR=/opt/project
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR
COPY --from=0 $PROJECT_DIR/target/eureka-server* $PROJECT_DIR/
#VOLUME /data/db
EXPOSE 8001 8002
CMD ["java", "-jar", "/opt/project/eureka-server.jar"]