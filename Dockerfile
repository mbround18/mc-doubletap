FROM gradle:jdk11@sha256:de235a119d103e76f854311a9df860803d526f09b60faa2f88e8f4e7194e14fd

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]