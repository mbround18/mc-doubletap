FROM gradle:jdk11@sha256:bbfe4efb5afea63f0faf0fb60f7956f294a93aa52f67cd528e51f71e03e23761

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]