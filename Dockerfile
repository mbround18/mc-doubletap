FROM gradle:jdk11@sha256:1cf8a26cf668cba735fb26f4b0f0b2b82c31bbdcedc5c0f484ef584ec3db9c30

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]