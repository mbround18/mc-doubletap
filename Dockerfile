FROM gradle:jdk11@sha256:3ab35e656305baae186529e7e23e9612365c273fb4a0a4f071fbab6c11fea4f9

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]