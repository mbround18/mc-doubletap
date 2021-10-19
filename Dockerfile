FROM gradle:jdk11@sha256:8ec236d0b09efd762e2d7d937937c39d9ad1ef608b2c93746f29af893d5bf184

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]