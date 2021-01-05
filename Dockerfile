FROM gradle:jdk11

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

CMD ["/usr/bin/gradle", "build"]