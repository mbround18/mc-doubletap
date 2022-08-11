FROM gradle:jdk11@sha256:1798dba7fc9c1ee4f37e137cbabbc9d6df8581004bd0b25c836b26dc3267cb03

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]