FROM maven:3.5.4-jdk-8

ENV PROJECT_DIR=/opt/project

RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR

ADD ./hystrix-dashboard/pom.xml $PROJECT_DIR
RUN mvn dependency:resolve

ADD ./hystrix-dashboard/src/ $PROJECT_DIR/src
RUN mvn install -DskipTests=true

FROM openjdk:8-jdk
ENV PROJECT_DIR=/opt/project
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR
COPY --from=0 $PROJECT_DIR/target/hystrix-dashboard* $PROJECT_DIR/
#VOLUME /data/db
EXPOSE 8788
CMD ["java", "-jar", "/opt/project/hystrix-dashboard.jar"]