FROM gradle:jdk11@sha256:c32899f30a7ce0d5926e2a2e7f929e6979b2da0ff68ca057b3c4b837bc788744

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]