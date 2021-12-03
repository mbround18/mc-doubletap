FROM gradle:jdk11@sha256:7c5d407096881bba4dcc46b76e407a1f6001977addcfb5a3918f26cf0829e894

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]