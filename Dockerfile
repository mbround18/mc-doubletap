FROM gradle:jdk11@sha256:16367c2d1a744ca33a69225177b391a4fec7efff392b9cf18cfba884ac539bb0

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]