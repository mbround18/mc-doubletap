FROM gradle:jdk11@sha256:1b9d4eb2e70d4cb6cebb671f1d995027cbe008600cd14b84ea25f3de1788f03d

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]