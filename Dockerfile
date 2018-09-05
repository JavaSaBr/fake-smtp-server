from openjdk:8-jdk-alpine as builder

WORKDIR /src/

ADD . . 

RUN export GRADLE_USER_HOME=$(pwd)/.gradle
RUN ./gradlew --build-cache build

FROM openjdk:8-jdk-alpine

VOLUME /tmp

EXPOSE 5080
EXPOSE 5081
EXPOSE 5025
EXPOSE 8000

COPY --from=builder /src/build/libs/fake-smtp-server.jar /opt/fake-smtp-server.jar

ENV JAVA_OPTS=""
#ENV JAVA_OPTS="-agentlib:jdwp=transport=dt_socket,address=8000,server=y,suspend=y"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /opt/fake-smtp-server.jar" ]
