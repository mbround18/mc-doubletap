FROM gradle:jdk11@sha256:37416de8042123c6d921a3470337b8398c5a6d119cf6583e6778efceaab9424e

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]