FROM gradle:jdk11@sha256:64a869d95a258a0a179763a320f3ffb85382214d04a9407afb1a4e485fcc00a8

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]